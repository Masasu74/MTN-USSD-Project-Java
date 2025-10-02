package com.bmt.webapp.controllers;

import com.bmt.webapp.models.Bundle;
import com.bmt.webapp.models.Purchase;
import com.bmt.webapp.repositories.BundleRepository;
import com.bmt.webapp.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.xml.bind.annotation.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    
    // Session storage for USSD flow state
    private Map<String, String> sessionStates = new ConcurrentHashMap<>();

    /**
     * POST /ussd - Main USSD endpoint
     * Handles USSD session requests from telecom providers
     * 
     * @param request USSD request containing session info and user input
     * @return USSD response with menu or confirmation
     */
    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String handleUssdRequest(@RequestBody(required = false) UssdRequest request, @ModelAttribute UssdRequest formRequest) {
        // Handle both XML and form requests
        UssdRequest ussdRequest = request != null ? request : formRequest;
        try {
            String sessionId = ussdRequest.getSessionId();
            String phoneNumber = ussdRequest.getPhoneNumber();
            String text = ussdRequest.getText();
            
            System.out.println("USSD Request - Session: " + sessionId + ", Phone: " + phoneNumber + ", Text: " + text);
            
            // Handle USSD flow without * separator
            // Each request is a single selection
            if (text == null || text.isEmpty()) {
                // Initial request - show main menu
                sessionStates.put(sessionId, "main_menu");
                return showMainMenuText();
            } else {
                // User made a selection - handle based on session state
                return handleUserSelectionText(text, phoneNumber, sessionId);
            }
            
        } catch (Exception e) {
            System.err.println("Error processing USSD request: " + e.getMessage());
            return "Sorry, an error occurred. Please try again later.";
        }
    }

    /**
     * Shows the main bundle menu with all bundles
     */
    private UssdResponse showMainMenu() {
        List<Bundle> bundles = bundleRepo.findByStatusOrderByIdDesc("Active");
        
        StringBuilder menu = new StringBuilder();
        menu.append("MTN Gwamon Bundles\n");
        menu.append("Valid till Sunday 23:59\n\n");
        
        int optionNumber = 1;
        for (Bundle bundle : bundles) {
            menu.append(optionNumber).append(") ").append(bundle.getUssdDisplayName()).append("\n");
            optionNumber++;
        }
        
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows the main bundle menu as plain text
     */
    private String showMainMenuText() {
        List<Bundle> bundles = bundleRepo.findByStatusOrderByIdDesc("Active");
        
        StringBuilder menu = new StringBuilder();
        menu.append("CON MTN Gwamon Bundles\n");
        menu.append("Valid till Sunday 23:59\n\n");
        
        int optionNumber = 1;
        for (Bundle bundle : bundles) {
            menu.append(optionNumber).append(") ").append(bundle.getUssdDisplayName()).append("\n");
            optionNumber++;
        }
        
        menu.append("0) Go back");
        
        return menu.toString();
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
            
            // Handle selection for any bundle (1-6)
            if (bundleIndex >= 1 && bundleIndex <= bundles.size()) {
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
            } else {
                return new UssdResponse("Invalid selection. Please try again.", "END");
            }
            
        } catch (NumberFormatException e) {
            return new UssdResponse("Invalid selection. Please try again.", "END");
        }
    }

    /**
     * Handles main menu selection as plain text
     */
    private String handleMainMenuSelectionText(String selection, String phoneNumber, String sessionId) {
        try {
            int bundleIndex = Integer.parseInt(selection);
            
            if (bundleIndex == 0) {
                return "END Thank you for using MTN Gwamon!";
            }
            
            List<Bundle> bundles = bundleRepo.findByStatusOrderByIdDesc("Active");
            
            // Handle selection for any bundle (1-6)
            if (bundleIndex >= 1 && bundleIndex <= bundles.size()) {
                Bundle selectedBundle = bundles.get(bundleIndex - 1);
                
                // Check weekend bundle restriction
                if (selectedBundle.isWeekend()) {
                    Calendar calendar = Calendar.getInstance();
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    if (dayOfWeek != Calendar.FRIDAY && dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                        return "END Gwamon' Weekend is only available from Friday to Sunday";
                    }
                }
                
                // Store selected bundle in session and show payment options
                sessionStates.put(sessionId, "payment_menu");
                sessionStates.put(sessionId + "_bundle", String.valueOf(bundleIndex));
                
                StringBuilder paymentMenu = new StringBuilder();
                paymentMenu.append("CON Pay ").append(selectedBundle.getUssdDisplayFormat()).append(" via:\n");
                paymentMenu.append("1. Airtime\n");
                paymentMenu.append("2. MoMo\n");
                paymentMenu.append("0. Go back");
                
                return paymentMenu.toString();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
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
     * Handles user selection based on session state
     */
    private String handleUserSelectionText(String selection, String phoneNumber, String sessionId) {
        String currentState = sessionStates.get(sessionId);
        
        if (currentState == null || "main_menu".equals(currentState)) {
            // User is in main menu - handle bundle selection
            return handleMainMenuSelectionText(selection, phoneNumber, sessionId);
        } else if ("payment_menu".equals(currentState)) {
            // User is in payment menu - handle payment selection
            return handlePaymentSelectionText(selection, phoneNumber, sessionId);
        } else {
            // Unknown state - reset to main menu
            sessionStates.put(sessionId, "main_menu");
            return showMainMenuText();
        }
    }

    /**
     * Handles payment method selection as plain text
     */
    private String handlePaymentSelectionText(String paymentSelection, String phoneNumber, String sessionId) {
        try {
            int paymentMethod = Integer.parseInt(paymentSelection);
            
            if (paymentMethod == 0) {
                // Go back to main menu
                sessionStates.put(sessionId, "main_menu");
                return showMainMenuText();
            }
            
            if (paymentMethod < 1 || paymentMethod > 2) {
                return "END Invalid payment method. Please try again.";
            }
            
            // Get selected bundle from session
            String bundleSelectionStr = sessionStates.get(sessionId + "_bundle");
            if (bundleSelectionStr == null) {
                return "END Session expired. Please start again.";
            }
            
            int bundleIndex = Integer.parseInt(bundleSelectionStr);
            List<Bundle> bundles = bundleRepo.findByStatusOrderByIdDesc("Active");
            if (bundleIndex < 1 || bundleIndex > bundles.size()) {
                return "END Invalid bundle selection.";
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
            
            // Clean up session
            sessionStates.remove(sessionId);
            sessionStates.remove(sessionId + "_bundle");
            
            String confirmation = String.format(
                "END Purchase successful!\n%s purchased via %s.\nThank you for using MTN Gwamon!",
                selectedBundle.getUssdDisplayFormat(),
                paymentMethodName
            );
            
            return confirmation;
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * USSD Request model
     */
    @XmlRootElement(name = "USSDRequest")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UssdRequest {
        @XmlElement(name = "sessionid")
        private String sessionId;
        
        @XmlElement(name = "msidn")
        private String phoneNumber;
        
        @XmlElement(name = "input")
        private String text;
        
        @XmlElement(name = "newrequest")
        private String newRequest;
        
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
        
        public String getNewRequest() { return newRequest; }
        public void setNewRequest(String newRequest) { this.newRequest = newRequest; }
    }

    /**
     * POST /ussd/xml - XML USSD endpoint
     * Handles XML USSD requests with manual XML parsing
     */
    @PostMapping(value = "/xml", consumes = "application/xml", produces = "text/plain")
    public String handleXmlUssdRequest(@RequestBody String xmlRequest) {
        try {
            // Manual XML parsing
            String sessionId = extractXmlValue(xmlRequest, "sessionid");
            String phoneNumber = extractXmlValue(xmlRequest, "msidn");
            String text = extractXmlValue(xmlRequest, "input");
            
            System.out.println("XML USSD Request - Session: " + sessionId + ", Phone: " + phoneNumber + ", Text: " + text);
            
            // Handle USSD flow without * separator
            // Each request is a single selection
            if (text == null || text.isEmpty()) {
                // Initial request - show main menu
                sessionStates.put(sessionId, "main_menu");
                return showMainMenuText();
            } else {
                // User made a selection - handle based on session state
                return handleUserSelectionText(text, phoneNumber, sessionId);
            }
            
        } catch (Exception e) {
            System.err.println("Error processing XML USSD request: " + e.getMessage());
            return "Sorry, an error occurred. Please try again later.";
        }
    }
    
    /**
     * Extract value from XML string
     */
    private String extractXmlValue(String xml, String tagName) {
        try {
            String startTag = "<" + tagName + ">";
            String endTag = "</" + tagName + ">";
            int startIndex = xml.indexOf(startTag);
            if (startIndex == -1) return "";
            startIndex += startTag.length();
            int endIndex = xml.indexOf(endTag, startIndex);
            if (endIndex == -1) return "";
            return xml.substring(startIndex, endIndex).trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * GET /ussd/test - Simple test endpoint for debugging
     */
    @GetMapping("/test")
    public UssdResponse testUssd() {
        return showMainMenu();
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
