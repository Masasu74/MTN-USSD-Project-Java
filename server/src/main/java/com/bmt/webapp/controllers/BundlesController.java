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
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * BundlesController - REST API Controller for MTN Gwamon Bundle Management
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
    public ResponseEntity<Bundle> getBundleById(@PathVariable int id) {
        Optional<Bundle> bundle = bundleRepo.findById(id);
        if (bundle.isPresent()) {
            return ResponseEntity.ok(bundle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/bundles - Create new bundle
     * Creates a new MTN Gwamon bundle with the provided details
     * Used by admin interface for adding new bundle offerings
     */
    @PostMapping
    public ResponseEntity<?> createBundle(@Valid @RequestBody BundleDto bundleDto) {
        try {
            // Create new bundle entity
            Bundle bundle = new Bundle();
            bundle.setName(bundleDto.getName());
            bundle.setPrice(bundleDto.getPrice());
            bundle.setData(bundleDto.getData());
            bundle.setMinutes(bundleDto.getMinutes());
            bundle.setSms(bundleDto.getSms());
            bundle.setValidUntil(bundleDto.getValidUntil());
            bundle.setWeekend(bundleDto.isWeekend());
            bundle.setStatus(bundleDto.getStatus());
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
            @PathVariable int id,
            @Valid @RequestBody BundleDto bundleDto
    ) {
        try {
            Optional<Bundle> existingBundleOpt = bundleRepo.findById(id);
            if (!existingBundleOpt.isPresent()) {
                return ResponseEntity.status(404)
                    .body(new ErrorResponse("Bundle not found"));
            }

            Bundle existingBundle = existingBundleOpt.get();

            // Update the existing bundle
            existingBundle.setName(bundleDto.getName());
            existingBundle.setPrice(bundleDto.getPrice());
            existingBundle.setData(bundleDto.getData());
            existingBundle.setMinutes(bundleDto.getMinutes());
            existingBundle.setSms(bundleDto.getSms());
            existingBundle.setValidUntil(bundleDto.getValidUntil());
            existingBundle.setWeekend(bundleDto.isWeekend());
            existingBundle.setStatus(bundleDto.getStatus());
            existingBundle.setUpdatedAt(new Date());

            Bundle updatedBundle = bundleRepo.save(existingBundle);
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
     * Removes a bundle from the system
     * Used by admin interface for removing bundle offerings
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBundle(@PathVariable int id) {
        Optional<Bundle> bundle = bundleRepo.findById(id);
        if (bundle.isPresent()) {
            bundleRepo.delete(bundle.get());
            SuccessResponse successResponse = new SuccessResponse(
                "Bundle deleted successfully!"
            );
            return ResponseEntity.ok(successResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Bundle not found with ID: " + id));
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
            // Validate bundle exists
            Optional<Bundle> bundleOpt = bundleRepo.findById(purchaseRequest.getBundleId());
            if (!bundleOpt.isPresent()) {
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Bundle not found"));
            }

            Bundle bundle = bundleOpt.get();

            // Check if bundle is active
            if (!"Active".equals(bundle.getStatus())) {
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Bundle is not available for purchase"));
            }

            // Create purchase record
            Purchase purchase = new Purchase();
            purchase.setPhoneNumber(purchaseRequest.getPhoneNumber());
            purchase.setPaymentMethod(purchaseRequest.getPaymentMethod());
            purchase.setStatus("completed");
            purchase.setPurchaseDate(new Date());
            purchase.setPurchaseId("PUR-" + System.currentTimeMillis());

            // Store bundle details in purchase
            purchase.setBundleName(bundle.getName());
            purchase.setBundlePrice(bundle.getPrice());
            purchase.setBundleData(bundle.getData());
            purchase.setBundleMinutes(bundle.getMinutes());
            purchase.setBundleSms(bundle.getSms());
            purchase.setBundleValidUntil(bundle.getValidUntil());
            purchase.setBundleIsWeekend(bundle.isWeekend());

            Purchase savedPurchase = purchaseRepo.save(purchase);

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
            purchases = purchaseRepo.findByPhoneNumber(phoneNumber, 
                Sort.by(Sort.Direction.DESC, "purchaseDate"));
        } else {
            purchases = purchaseRepo.findAll(Sort.by(Sort.Direction.DESC, "purchaseDate"));
        }
        return ResponseEntity.ok(purchases);
    }

    /**
     * PurchaseRequest - Inner class for purchase request data
     * Used for handling purchase requests from Frontend, Postman, or USSD
     */
    public static class PurchaseRequest {
        private int bundleId;
        private String phoneNumber;
        private String paymentMethod;

        // Getters and Setters
        public int getBundleId() {
            return bundleId;
        }

        public void setBundleId(int bundleId) {
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
    }
}
