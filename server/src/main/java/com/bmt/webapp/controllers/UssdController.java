package com.bmt.webapp.controllers;

import com.bmt.webapp.models.Bundle;
import com.bmt.webapp.models.Purchase;
import com.bmt.webapp.repositories.BundleRepository;
import com.bmt.webapp.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * UssdController - Handles USSD requests for MTN Gwamon Bundle system
 * 
 * This controller processes USSD sessions and provides the interactive
 * menu system for customers to browse and purchase bundles.
 * 
 * USSD Flow:
 * 1. Customer dials *154# (or similar USSD code)
 * 2. System shows main menu with available bundles
 * 3. Customer selects bundle option
 * 4. System shows payment options
 * 5. Customer completes purchase
 * 
 * Testing:
 * - Use USSD simulators like ussd.dev, Speso, or Arkesel
 * - Test with your own phone using ngrok to expose local server
 * - Use Postman to simulate USSD requests
 */
@RestController
@RequestMapping("/ussd")
@CrossOrigin(origins = {"*"})
public class UssdController {
    
    @Autowired
    private BundleRepository bundleRepo;
    
    @Autowired
    private PurchaseRepository purchaseRepo;

    /**
     * POST /ussd - Main USSD endpoint
     * Handles USSD session requests from telecom providers
     * 
     * @param request USSD request containing session info and user input
     * @return USSD response with menu or confirmation
     */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public UssdResponse handleUssdRequest(@ModelAttribute UssdRequest request) {
        try {
            String sessionId = request.getSessionId();
            String phoneNumber = request.getPhoneNumber();
            String text = request.getText();
            
            System.out.println("USSD Request - Session: " + sessionId + ", Phone: " + phoneNumber + ", Text: " + text);
            
            // Parse user input
            String[] userInput = text != null && !text.isEmpty() ? text.split("\\*") : new String[0];
            
            // Handle different stages of USSD flow
            if (userInput.length == 0) {
                // Initial request - show main menu
                return showMainMenu();
            } else if (userInput.length == 1) {
                // User selected an option from main menu
                return handleMainMenuSelection(userInput[0], phoneNumber);
            } else if (userInput.length == 2) {
                // User selected payment method
                return handlePaymentSelection(userInput[0], userInput[1], phoneNumber);
            } else {
                // Invalid input
                return new UssdResponse("Invalid selection. Please try again.", "END");
            }
            
        } catch (Exception e) {
            System.err.println("Error processing USSD request: " + e.getMessage());
            return new UssdResponse("Sorry, an error occurred. Please try again later.", "END");
        }
    }

    /**
     * Shows the main bundle menu
     */
    private UssdResponse showMainMenu() {
        List<Bundle> bundles = bundleRepo.findByStatusOrderByIdDesc("Active");
        
        StringBuilder menu = new StringBuilder();
        menu.append("MTN Gwamon Bundles\n");
        menu.append("Valid till Sunday 23:59\n\n");
        
        int optionNumber = 1;
        for (Bundle bundle : bundles) {
            if (optionNumber <= 4) { // Limit to 4 options for USSD display
                menu.append(optionNumber).append(") ").append(bundle.getUssdDisplayName()).append("\n");
                optionNumber++;
            }
        }
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Handles main menu selection
     */
    private UssdResponse handleMainMenuSelection(String selection, String phoneNumber) {
        try {
            int bundleIndex = Integer.parseInt(selection);
            
            if (bundleIndex == 0) {
                return new UssdResponse("Thank you for using MTN Gwamon!", "END");
            }
            
            List<Bundle> bundles = bundleRepo.findByStatusOrderByIdDesc("Active");
            if (bundleIndex < 1 || bundleIndex > bundles.size()) {
                return new UssdResponse("Invalid selection. Please try again.", "END");
            }
            
            Bundle selectedBundle = bundles.get(bundleIndex - 1);
            
            // Check weekend bundle restriction
            if (selectedBundle.isWeekend()) {
                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek != Calendar.FRIDAY && dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                    return new UssdResponse("Gwamon' Weekend is only available from Friday to Sunday", "END");
                }
            }
            
            // Show payment options
            StringBuilder paymentMenu = new StringBuilder();
            paymentMenu.append("Pay ").append(selectedBundle.getUssdDisplayFormat()).append(" via:\n");
            paymentMenu.append("1. Airtime\n");
            paymentMenu.append("2. MoMo\n");
            paymentMenu.append("0. Go back");
            
            return new UssdResponse(paymentMenu.toString(), "CON");
            
        } catch (NumberFormatException e) {
            return new UssdResponse("Invalid selection. Please try again.", "END");
        }
    }

    /**
     * Handles payment method selection
     */
    private UssdResponse handlePaymentSelection(String bundleSelection, String paymentSelection, String phoneNumber) {
        try {
            int bundleIndex = Integer.parseInt(bundleSelection);
            int paymentMethod = Integer.parseInt(paymentSelection);
            
            if (paymentMethod == 0) {
                return showMainMenu();
            }
            
            if (paymentMethod < 1 || paymentMethod > 2) {
                return new UssdResponse("Invalid payment method. Please try again.", "END");
            }
            
            List<Bundle> bundles = bundleRepo.findByStatusOrderByIdDesc("Active");
            if (bundleIndex < 1 || bundleIndex > bundles.size()) {
                return new UssdResponse("Invalid bundle selection.", "END");
            }
            
            Bundle selectedBundle = bundles.get(bundleIndex - 1);
            String paymentMethodName = paymentMethod == 1 ? "Airtime" : "MoMo";
            
            // Create purchase record
            Purchase purchase = new Purchase();
            purchase.setPhoneNumber(phoneNumber);
            purchase.setPaymentMethod(paymentMethodName);
            purchase.setStatus("completed");
            purchase.setPurchaseDate(new Date());
            purchase.setPurchaseId("PUR-" + System.currentTimeMillis());
            purchase.setBundle(selectedBundle);
            
            purchaseRepo.save(purchase);
            
            String confirmation = String.format(
                "Purchase successful!\n%s purchased via %s.\nThank you for using MTN Gwamon!",
                selectedBundle.getUssdDisplayFormat(),
                paymentMethodName
            );
            
            return new UssdResponse(confirmation, "END");
            
        } catch (NumberFormatException e) {
            return new UssdResponse("Invalid selection. Please try again.", "END");
        }
    }

    /**
     * USSD Request model
     */
    public static class UssdRequest {
        private String sessionId;
        private String phoneNumber;
        private String text;
        private String serviceCode;

        // Getters and Setters
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        
        public String getServiceCode() { return serviceCode; }
        public void setServiceCode(String serviceCode) { this.serviceCode = serviceCode; }
    }

    /**
     * USSD Response model
     */
    public static class UssdResponse {
        private String message;
        private String sessionState; // CON or END

        public UssdResponse(String message, String sessionState) {
            this.message = message;
            this.sessionState = sessionState;
        }

        // Getters and Setters
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        
        public String getSessionState() { return sessionState; }
        public void setSessionState(String sessionState) { this.sessionState = sessionState; }
    }
}
