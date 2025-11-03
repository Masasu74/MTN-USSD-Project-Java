package com.bmt.webapp.controllers;

import com.bmt.webapp.models.Bundle;
import com.bmt.webapp.models.BundleDto;
import com.bmt.webapp.models.Purchase;
import com.bmt.webapp.models.ErrorResponse;
import com.bmt.webapp.models.SuccessResponse;
import com.bmt.webapp.repositories.BundleRepository;
import com.bmt.webapp.repositories.PurchaseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * BundlesController - REST API Controller for MTN YOLO USSD Management
 * 
 * This controller handles all bundle operations for the USSD application:
 * - Bundle CRUD operations (Create, Read, Update, Delete)
 * - Bundle purchase functionality
 * - Purchase history tracking
 * 
 * Supports multiple access methods:
 * - Frontend web interface
 * - Postman API testing
 * - USSD code like *154# (for future USSD integration)
 * 
 * Endpoints:
 * - GET /api/bundles - Get all bundles
 * - GET /api/bundles/{id} - Get bundle by ID
 * - POST /api/bundles - Create new bundle
 * - PUT /api/bundles/{id} - Update bundle
 * - DELETE /api/bundles/{id} - Delete bundle
 * - POST /api/bundles/purchase - Purchase a bundle
 * - GET /api/purchases - Get purchase history
 */
@RestController
@RequestMapping("/api/bundles")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:3002"})
public class BundlesController {
    
    @Autowired
    private BundleRepository bundleRepo;
    
    @Autowired
    private PurchaseRepository purchaseRepo;

    /**
     * GET /api/bundles - Get all bundles
     * Returns all available bundles sorted by ID (newest first)
     * Used by Frontend, Postman, and USSD applications
     */
    @GetMapping({"","/"})
    public ResponseEntity<List<Bundle>> getAllBundles() {
        List<Bundle> bundles = bundleRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return ResponseEntity.ok(bundles);
    }
    
    /**
     * GET /api/bundles/{id} - Get bundle by ID
     * Returns a specific bundle by its ID
     * Used for bundle details and purchase operations
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bundle> getBundleById(@PathVariable Long id) {
        Optional<Bundle> bundle = bundleRepo.findById(id);
        if (bundle.isPresent()) {
            return ResponseEntity.ok(bundle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/bundles/category/{categoryId} - Get bundles by category
     * Returns all active bundles for a specific category
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Bundle>> getBundlesByCategory(@PathVariable Long categoryId) {
        List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(categoryId);
        return ResponseEntity.ok(bundles);
    }

    /**
     * POST /api/bundles - Create new bundle
     * Creates a new MTN YOLO USSD bundle with the provided details
     * Used by admin interface for adding new bundle offerings
     */
    @PostMapping
    public ResponseEntity<?> createBundle(@Valid @RequestBody BundleDto bundleDto) {
        try {
            // Create new bundle entity
            Bundle bundle = new Bundle();
            bundle.setCode("bundle_" + System.currentTimeMillis()); // Generate unique code
            bundle.setCategoryId(2L); // Default to Gwamon category
            bundle.setDisplayOrder(0); // Default display order
            bundle.setName(bundleDto.getName());
            bundle.setPrice(java.math.BigDecimal.valueOf(bundleDto.getPrice()));
            bundle.setDataMb(bundleDto.getData());
            bundle.setMinutes(bundleDto.getMinutes());
            bundle.setSms(bundleDto.getSms());
            bundle.setValidityHours(bundleDto.getValidUntil());
            bundle.setIsWeekend(bundleDto.isWeekend());
            bundle.setIsActive(true);
            bundle.setCreatedAt(new Date());
            bundle.setUpdatedAt(new Date());

            Bundle savedBundle = bundleRepo.save(bundle);
            
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedBundle.getId())
                    .toUri();
            
            SuccessResponse successResponse = new SuccessResponse(
                "Bundle created successfully!", 
                savedBundle
            );
            
            return ResponseEntity.created(location).body(successResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Failed to create bundle: " + e.getMessage()));
        }
    }

    /**
     * PUT /api/bundles/{id} - Update bundle
     * Updates an existing bundle with new information
     * Used by admin interface for modifying bundle details
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBundle(
            @PathVariable Long id,
            @Valid @RequestBody BundleDto bundleDto
    ) {
        try {
            // Log the received data for debugging
            System.out.println("Received update request for bundle ID: " + id);
            System.out.println("BundleDto data: " + bundleDto.getName() + ", isWeekend: " + bundleDto.isWeekend());
            
            Optional<Bundle> existingBundleOpt = bundleRepo.findById(id);
            if (!existingBundleOpt.isPresent()) {
                return ResponseEntity.status(404)
                    .body(new ErrorResponse("Bundle not found"));
            }

            Bundle existingBundle = existingBundleOpt.get();

            // Update the existing bundle
            existingBundle.setName(bundleDto.getName());
            existingBundle.setPrice(java.math.BigDecimal.valueOf(bundleDto.getPrice()));
            existingBundle.setDataMb(bundleDto.getData());
            existingBundle.setMinutes(bundleDto.getMinutes());
            existingBundle.setSms(bundleDto.getSms());
            existingBundle.setValidityHours(bundleDto.getValidUntil());
            existingBundle.setIsWeekend(bundleDto.isWeekend());
            existingBundle.setIsActive(true);
            existingBundle.setUpdatedAt(new Date());

            Bundle updatedBundle = bundleRepo.save(existingBundle);
            System.out.println("Bundle updated successfully. Final isWeekend value: " + updatedBundle.isWeekend());
            
            SuccessResponse successResponse = new SuccessResponse(
                "Bundle updated successfully!", 
                updatedBundle
            );
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Failed to update bundle: " + e.getMessage()));
        }
    }

    /**
     * DELETE /api/bundles/{id} - Delete bundle
     * Removes a bundle from the system along with all associated purchases
     * Used by admin interface for removing bundle offerings
     * Note: This will automatically delete all associated purchases (cascading delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBundle(@PathVariable Long id) {
        try {
            Optional<Bundle> bundleOpt = bundleRepo.findById(id);
            if (!bundleOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Bundle not found with ID: " + id));
            }

            Bundle bundle = bundleOpt.get();
            
            // Get all purchases associated with this bundle
            List<Purchase> purchases = purchaseRepo.findByBundleId(id);
            int purchaseCount = purchases.size();
            
            // Delete all associated purchases first (cascading delete)
            if (!purchases.isEmpty()) {
                purchaseRepo.deleteAll(purchases);
                System.out.println("Deleted " + purchaseCount + " associated purchase(s) for bundle ID: " + id);
            }

            // Delete the bundle
            bundleRepo.delete(bundle);
            
            String message = purchaseCount > 0 ? 
                "Bundle and " + purchaseCount + " associated purchase(s) deleted successfully!" :
                "Bundle deleted successfully!";
                
            SuccessResponse successResponse = new SuccessResponse(message);
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Failed to delete bundle: " + e.getMessage()));
        }
    }

    /**
     * POST /api/bundles/purchase - Purchase a bundle
     * Handles bundle purchase transactions
     * Used by customers through Frontend, Postman, or USSD
     */
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseBundle(@RequestBody PurchaseRequest purchaseRequest) {
        try {
            // Validate phone number format
            String phoneNumber = purchaseRequest.getPhoneNumber();
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Phone number is required"));
            }
            
            // Remove any spaces or special characters
            phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
            
            // Check if phone number is 10 digits and starts with 078 or 079
            if (phoneNumber.length() != 10 || (!phoneNumber.startsWith("078") && !phoneNumber.startsWith("079"))) {
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Phone number must be 10 digits and start with 078 or 079"));
            }

            // Validate bundle exists
            Optional<Bundle> bundleOpt = bundleRepo.findById(purchaseRequest.getBundleId());
            if (!bundleOpt.isPresent()) {
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Bundle not found"));
            }

            Bundle bundle = bundleOpt.get();

            // Check if bundle is active
            if (bundle.getIsActive() == null || !bundle.getIsActive()) {
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Bundle is not available for purchase"));
            }

            // Check weekend bundle restriction
            if (bundle.isWeekend()) {
                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                // Sunday = 1, Monday = 2, ..., Saturday = 7
                // Weekend bundles can only be purchased on Friday (6), Saturday (7), or Sunday (1)
                if (dayOfWeek != Calendar.FRIDAY && dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                    return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Weekend bundles can only be purchased from Friday to Sunday"));
                }
            }

            // Create purchase record
            Purchase purchase = new Purchase();
            purchase.setPhoneNumber(purchaseRequest.getPhoneNumber());
            purchase.setPaymentMethod(purchaseRequest.getPaymentMethod());
            purchase.setStatus("completed");
            purchase.setPurchasedAt(new Date());
            purchase.setCompletedAt(new Date());
            purchase.setPurchaseId("PUR-" + System.currentTimeMillis());
            purchase.setBundleId(bundle.getId());
            purchase.setBundle(bundle); // Set bundle relationship explicitly
            purchase.setAmount(bundle.getPrice());
            
            // Save sessionId if provided
            if (purchaseRequest.getSessionId() != null && !purchaseRequest.getSessionId().trim().isEmpty()) {
                purchase.setSessionId(purchaseRequest.getSessionId());
            }
            
            // Save bundle snapshot as JSON
            try {
                com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                String bundleSnapshot = objectMapper.writeValueAsString(bundle);
                purchase.setBundleSnapshot(bundleSnapshot);
            } catch (Exception e) {
                System.err.println("Warning: Failed to create bundle snapshot: " + e.getMessage());
            }

            Purchase savedPurchase = purchaseRepo.save(purchase);
            System.out.println("✓ Purchase saved with ID: " + savedPurchase.getId() + " for bundle: " + bundle.getName());

            SuccessResponse successResponse = new SuccessResponse(
                "Bundle purchased successfully! Your " + bundle.getName() + " is now active.",
                savedPurchase
            );

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Failed to process purchase: " + e.getMessage()));
        }
    }

    /**
     * GET /api/purchases - Get purchase history
     * Returns all purchases or filtered by phone number
     * Used for tracking customer purchases
     */
    @GetMapping("/purchases")
    public ResponseEntity<List<Purchase>> getPurchases(
            @RequestParam(required = false) String phoneNumber) {
        List<Purchase> purchases;
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            purchases = purchaseRepo.findByPhoneNumber(phoneNumber);
        } else {
            purchases = purchaseRepo.findAllPurchases();
        }
        return ResponseEntity.ok(purchases);
    }

    /**
     * PurchaseRequest - Inner class for purchase request data
     * Used for handling purchase requests from Frontend, Postman, or USSD
     */
    public static class PurchaseRequest {
        private Long bundleId;
        private String phoneNumber;
        private String paymentMethod;
        private String sessionId; // Optional session ID

        // Getters and Setters
        public Long getBundleId() {
            return bundleId;
        }

        public void setBundleId(Long bundleId) {
            this.bundleId = bundleId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }
}
