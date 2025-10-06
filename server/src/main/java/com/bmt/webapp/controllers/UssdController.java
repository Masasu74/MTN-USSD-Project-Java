package com.bmt.webapp.controllers;

import com.bmt.webapp.models.Bundle;
import com.bmt.webapp.models.Purchase;
import com.bmt.webapp.models.UssdSession;
import com.bmt.webapp.repositories.BundleRepository;
import com.bmt.webapp.repositories.PurchaseRepository;
import com.bmt.webapp.repositories.UssdSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import jakarta.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * UssdController - Handles USSD requests for MTN YOLO USSD system
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
    
    @Autowired
    private UssdSessionRepository sessionRepo;

    /**
     * POST /ussd - Main USSD endpoint
     * Handles USSD session requests from telecom providers
     * 
     * @param request USSD request containing session info and user input
     * @return USSD response with menu or confirmation
     */
    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String handleUssdRequest(@RequestParam(required = false) String sessionid,
                                   @RequestParam(required = false) String msidn,
                                   @RequestParam(required = false) String input,
                                   @RequestBody(required = false) String xmlBody) {
        try {
            String sessionId = sessionid;
            String phoneNumber = msidn;
            String text = input;
            
            // Handle XML request if form data is null
            if (sessionId == null && phoneNumber == null && text == null && xmlBody != null) {
                sessionId = extractXmlValue(xmlBody, "sessionid");
                phoneNumber = extractXmlValue(xmlBody, "msidn");
                text = extractXmlValue(xmlBody, "input");
            }
            
            System.out.println("USSD Request - Session: " + sessionId + ", Phone: " + phoneNumber + ", Text: " + text);
            
            // Handle USSD flow with database session management
            if (text == null || text.isEmpty()) {
                // Initial request - create or get session and show main menu
                return handleInitialRequest(sessionId, phoneNumber);
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
     * Shows the main YOLO menu with all options
     */
    private UssdResponse showMainMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("YOLO\n");
        menu.append("Choose an option:\n\n");
        menu.append("99) Triple Data Promo\n");
        menu.append("0) Gwamon'\n");
        menu.append("1) YOLO Voice\n");
        menu.append("2) YOLO Internet\n");
        menu.append("3) Other Bundles\n");
        menu.append("4) DesaDe\n");
        menu.append("5) Balance check\n");
        menu.append("6) FoLeva\n");
        menu.append("7) Ihereze\n");
        menu.append("8) YOLO Star\n");
        menu.append("n) Next");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows the main YOLO menu as plain text
     */
    private String showMainMenuText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON YOLO\n");
        menu.append("Choose an option:\n\n");
        menu.append("99) Triple Data Promo\n");
        menu.append("0) Gwamon'\n");
        menu.append("1) YOLO Voice\n");
        menu.append("2) YOLO Internet\n");
        menu.append("3) Other Bundles\n");
        menu.append("4) DesaDe\n");
        menu.append("5) Balance check\n");
        menu.append("6) FoLeva\n");
        menu.append("7) Ihereze\n");
        menu.append("8) YOLO Star\n");
        menu.append("n) Next");
        
        return menu.toString();
    }

    /**
     * Handles main YOLO menu selection
     */
    private UssdResponse handleMainMenuSelection(String selection, String phoneNumber) {
        try {
            // Handle special text inputs first
            if ("n".equalsIgnoreCase(selection) || "next".equalsIgnoreCase(selection)) {
                return showNextPage();
            }
            
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 99) {
                return showTripleDataPromo();
            } else if (optionIndex == 0) {
                // Gwamon' option - show Gwamon bundles
                return showGwamonBundles();
            } else if (optionIndex == 1) {
                return showYoloVoice();
            } else if (optionIndex == 2) {
                return new UssdResponse("YOLO Internet - Coming soon!", "END");
            } else if (optionIndex == 3) {
                return new UssdResponse("Other Bundles - Coming soon!", "END");
            } else if (optionIndex == 4) {
                return new UssdResponse("DesaDe - Coming soon!", "END");
            } else if (optionIndex == 5) {
                return new UssdResponse("Balance check - Coming soon!", "END");
            } else if (optionIndex == 6) {
                return new UssdResponse("FoLeva - Coming soon!", "END");
            } else if (optionIndex == 7) {
                return new UssdResponse("Ihereze - Coming soon!", "END");
            } else if (optionIndex == 8) {
                return new UssdResponse("YOLO Star - Coming soon!", "END");
            } else {
                return new UssdResponse("Invalid selection. Please try again.", "END");
            }
            
        } catch (NumberFormatException e) {
            return new UssdResponse("Invalid selection. Please try again.", "END");
        }
    }

    /**
     * Shows the next page with additional options
     */
    private UssdResponse showNextPage() {
        StringBuilder menu = new StringBuilder();
        menu.append("YOLO - Page 2\n");
        menu.append("Choose an option:\n\n");
        menu.append("9) Hindura Ururimi(Language)\n");
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows Triple Data Promo menu
     */
    private UssdResponse showTripleDataPromo() {
        StringBuilder menu = new StringBuilder();
        menu.append("Triple Data Promo\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) 200Frw=600MB+300MB(Night)/24hrs\n");
        menu.append("2) 500Frw=2GB+1GB (Night)/24hrs\n");
        menu.append("3) 1000Frw=2GB+1GB (Night)/7dys\n");
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows YOLO Voice menu
     */
    private UssdResponse showYoloVoice() {
        StringBuilder menu = new StringBuilder();
        menu.append("YOLO Voice\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) 100Frw=50Mins+20SMS/24hrs\n");
        menu.append("2) 200Frw=250Mins+20SMS/24hrs\n");
        menu.append("3) 500Frw=800Mins+20SMS/7Days\n");
        menu.append("4) 1000Frw=(120 Mins+1GB) per day /7days\n");
        menu.append("n) Next\n");
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows Gwamon bundles menu
     */
    private UssdResponse showGwamonBundles() {
        List<Bundle> bundles = bundleRepo.findByStatusOrderByIdDesc("Active");
        
        StringBuilder menu = new StringBuilder();
        menu.append("Gwamon' Bundles\n");
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
     * Handles main YOLO menu selection as plain text
     */
    private String handleMainMenuSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            // Handle special text inputs first
            if ("n".equalsIgnoreCase(selection) || "next".equalsIgnoreCase(selection)) {
                session.setCurrentState("next_page");
                sessionRepo.save(session);
                return showNextPageText();
            }
            
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 99) {
                session.setCurrentState("triple_data_promo");
                sessionRepo.save(session);
                return showTripleDataPromoText();
            } else if (optionIndex == 0) {
                // Gwamon' option - show Gwamon bundles
                session.setCurrentState("gwamon_menu");
                sessionRepo.save(session);
                return showGwamonBundlesText();
            } else if (optionIndex == 1) {
                session.setCurrentState("yolo_voice");
                sessionRepo.save(session);
                return showYoloVoiceText();
            } else if (optionIndex == 2) {
                return "END YOLO Internet - Coming soon!";
            } else if (optionIndex == 3) {
                return "END Other Bundles - Coming soon!";
            } else if (optionIndex == 4) {
                return "END DesaDe - Coming soon!";
            } else if (optionIndex == 5) {
                return "END Balance check - Coming soon!";
            } else if (optionIndex == 6) {
                return "END FoLeva - Coming soon!";
            } else if (optionIndex == 7) {
                return "END Ihereze - Coming soon!";
            } else if (optionIndex == 8) {
                return "END YOLO Star - Coming soon!";
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }


    /**
     * Shows the next page as plain text
     */
    private String showNextPageText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON YOLO - Page 2\n");
        menu.append("Choose an option:\n\n");
        menu.append("9) Hindura Ururimi(Language)\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows Triple Data Promo menu as plain text
     */
    private String showTripleDataPromoText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Triple Data Promo\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) 200Frw=600MB+300MB(Night)/24hrs\n");
        menu.append("2) 500Frw=2GB+1GB (Night)/24hrs\n");
        menu.append("3) 1000Frw=2GB+1GB (Night)/7dys\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows YOLO Voice menu as plain text
     */
    private String showYoloVoiceText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON YOLO Voice\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) 100Frw=50Mins+20SMS/24hrs\n");
        menu.append("2) 200Frw=250Mins+20SMS/24hrs\n");
        menu.append("3) 500Frw=800Mins+20SMS/7Days\n");
        menu.append("4) 1000Frw=(120 Mins+1GB) per day /7days\n");
        menu.append("n) Next\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows YOLO Voice next page as plain text
     */
    private String showYoloVoiceNextPageText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON YOLO Voice - Page 2\n");
        menu.append("Choose an option:\n\n");
        menu.append("5) 2000Frw=4000Mins+100SMS/30 Days\n");
        menu.append("6) DesaDe\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Handles next page selection
     */
    private String handleNextPageSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            } else if (optionIndex == 9) {
                return "END Hindura Ururimi(Language) - Coming soon!";
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles Triple Data Promo selection
     */
    private String handleTripleDataPromoSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            } else if (optionIndex == 1) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(1L); // 200Frw bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("triple_data_promo"); // Remember we came from Triple Data Promo
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 2) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(2L); // 500Frw bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("triple_data_promo"); // Remember we came from Triple Data Promo
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 3) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(3L); // 1000Frw bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("triple_data_promo"); // Remember we came from Triple Data Promo
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles YOLO Voice selection
     */
    private String handleYoloVoiceSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            // Handle special text inputs first
            if ("n".equalsIgnoreCase(selection) || "next".equalsIgnoreCase(selection)) {
                session.setCurrentState("yolo_voice_next");
                sessionRepo.save(session);
                return showYoloVoiceNextPageText();
            }
            
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            } else if (optionIndex == 1) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(10L); // 100Frw voice bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_voice"); // Remember we came from YOLO Voice
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 2) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(11L); // 200Frw voice bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_voice"); // Remember we came from YOLO Voice
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 3) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(12L); // 500Frw voice bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_voice"); // Remember we came from YOLO Voice
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 4) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(13L); // 1000Frw voice bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_voice"); // Remember we came from YOLO Voice
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles YOLO Voice next page selection
     */
    private String handleYoloVoiceNextPageSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to YOLO Voice main page
                session.setCurrentState("yolo_voice");
                sessionRepo.save(session);
                return showYoloVoiceText();
            } else if (optionIndex == 5) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(14L); // 2000Frw voice bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_voice"); // Remember we came from YOLO Voice
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 6) {
                return "END DesaDe - Coming soon!";
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Shows payment menu as plain text
     */
    private String showPaymentMenuText() {
        StringBuilder paymentMenu = new StringBuilder();
        paymentMenu.append("CON Payment Options\n");
        paymentMenu.append("Choose payment method:\n\n");
        paymentMenu.append("1. Airtime\n");
        paymentMenu.append("2. MoMo\n");
        paymentMenu.append("0. Go back");
        
        return paymentMenu.toString();
    }

    /**
     * Shows Gwamon bundles menu as plain text
     */
    private String showGwamonBundlesText() {
        List<Bundle> bundles = bundleRepo.findByStatusOrderByIdDesc("Active");
        
        StringBuilder menu = new StringBuilder();
        menu.append("CON Gwamon' Bundles\n");
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
     * Handles Gwamon bundles menu selection
     */
    private String handleGwamonMenuSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int bundleIndex = Integer.parseInt(selection);
            
            if (bundleIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
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
                session.setCurrentState("payment_menu");
                session.setSelectedBundleId((long) selectedBundle.getId());
                sessionRepo.save(session);
                
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
                "Purchase successful!\n%s purchased via %s.\nThank you for using MTN YOLO USSD!",
                selectedBundle.getUssdDisplayFormat(),
                paymentMethodName
            );
            
            return new UssdResponse(confirmation, "END");
            
        } catch (NumberFormatException e) {
            return new UssdResponse("Invalid selection. Please try again.", "END");
        }
    }

    /**
     * Handles initial USSD request - creates or retrieves session
     */
    private String handleInitialRequest(String sessionId, String phoneNumber) {
        try {
            // Clean up expired sessions first
            sessionRepo.deactivateExpiredSessions(LocalDateTime.now());
            
            // Check for existing active sessions for this phone number
            List<UssdSession> existingSessions = sessionRepo.findActiveSessionsByPhoneNumber(phoneNumber);
            if (!existingSessions.isEmpty()) {
                // Deactivate any existing sessions for this phone number
                for (UssdSession existingSession : existingSessions) {
                    existingSession.deactivate();
                    sessionRepo.save(existingSession);
                }
            }
            
            // Try to find existing session with this session ID
            Optional<UssdSession> existingSession = sessionRepo.findActiveSessionBySessionId(sessionId);
            
            if (existingSession.isPresent()) {
                UssdSession session = existingSession.get();
                if (session.isExpired()) {
                    // Session expired, deactivate old session and create new one
                    session.deactivate();
                    sessionRepo.save(session);
                    System.out.println("Session " + sessionId + " was expired, creating new session for " + phoneNumber);
                    return createNewSessionAndShowMenu(sessionId, phoneNumber);
                } else {
                    // Extend existing session and show menu
                    session.extendSession();
                    sessionRepo.save(session);
                    return showMainMenuText();
                }
            } else {
                // Create new session
                return createNewSessionAndShowMenu(sessionId, phoneNumber);
            }
        } catch (Exception e) {
            System.err.println("Error handling initial request: " + e.getMessage());
            return "END Sorry, an error occurred. Please try again later.";
        }
    }
    
    /**
     * Creates new session and shows main menu
     */
    private String createNewSessionAndShowMenu(String sessionId, String phoneNumber) {
        UssdSession newSession = new UssdSession(sessionId, phoneNumber);
        sessionRepo.save(newSession);
        return showMainMenuText();
    }
    
    /**
     * Validates session and returns appropriate error message if invalid
     */
    private String validateSession(String sessionId, String phoneNumber) {
        Optional<UssdSession> sessionOpt = sessionRepo.findActiveSessionBySessionId(sessionId);
        
        if (!sessionOpt.isPresent()) {
            System.out.println("Session " + sessionId + " not found for phone " + phoneNumber);
            return "END Your session has expired. Please dial *154# again to start a new session.";
        }
        
        UssdSession session = sessionOpt.get();
        if (session.isExpired()) {
            System.out.println("Session " + sessionId + " expired for phone " + phoneNumber + " at " + session.getExpiresAt());
            session.deactivate();
            sessionRepo.save(session);
            return "END Your session has expired due to inactivity. Please dial *154# again to start a new session.";
        }
        
        return null; // Session is valid
    }

    /**
     * Handles user selection based on session state
     */
    private String handleUserSelectionText(String selection, String phoneNumber, String sessionId) {
        try {
            // Validate input
            if (selection == null || selection.trim().isEmpty()) {
                return "END Invalid input. Please try again.";
            }
            
            // Validate session
            String sessionError = validateSession(sessionId, phoneNumber);
            if (sessionError != null) {
                return sessionError;
            }
            
            // Get session from database (we know it exists and is valid from validation above)
            UssdSession session = sessionRepo.findActiveSessionBySessionId(sessionId).get();
            
            // Update session with new input
            session.setLastInput(selection.trim());
            session.extendSession();
            sessionRepo.save(session);
            
            String currentState = session.getCurrentState();
            
            if (currentState == null || "main_menu".equals(currentState)) {
                // User is in main YOLO menu - handle option selection
                return handleMainMenuSelectionText(selection.trim(), phoneNumber, session);
            } else if ("next_page".equals(currentState)) {
                // User is in next page - handle next page selection
                return handleNextPageSelectionText(selection.trim(), phoneNumber, session);
            } else if ("triple_data_promo".equals(currentState)) {
                // User is in Triple Data Promo menu - handle selection
                return handleTripleDataPromoSelectionText(selection.trim(), phoneNumber, session);
            } else if ("yolo_voice".equals(currentState)) {
                // User is in YOLO Voice menu - handle selection
                return handleYoloVoiceSelectionText(selection.trim(), phoneNumber, session);
            } else if ("yolo_voice_next".equals(currentState)) {
                // User is in YOLO Voice next page - handle selection
                return handleYoloVoiceNextPageSelectionText(selection.trim(), phoneNumber, session);
            } else if ("gwamon_menu".equals(currentState)) {
                // User is in Gwamon bundles menu - handle bundle selection
                return handleGwamonMenuSelectionText(selection.trim(), phoneNumber, session);
            } else if ("payment_menu".equals(currentState)) {
                // User is in payment menu - handle payment selection
                return handlePaymentSelectionText(selection.trim(), phoneNumber, session);
            } else {
                // Unknown state - reset to main menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            }
        } catch (Exception e) {
            System.err.println("Error handling user selection: " + e.getMessage());
            return "END Sorry, an error occurred. Please try again later.";
        }
    }

    /**
     * Handles payment method selection as plain text
     */
    private String handlePaymentSelectionText(String paymentSelection, String phoneNumber, UssdSession session) {
        try {
            int paymentMethod = Integer.parseInt(paymentSelection);
            
            if (paymentMethod == 0) {
                // Go back to previous menu based on lastInput
                String lastInput = session.getLastInput();
                if ("triple_data_promo".equals(lastInput)) {
                    session.setCurrentState("triple_data_promo");
                    sessionRepo.save(session);
                    return showTripleDataPromoText();
                } else if ("yolo_voice".equals(lastInput)) {
                    session.setCurrentState("yolo_voice");
                    sessionRepo.save(session);
                    return showYoloVoiceText();
                } else {
                    // Default to Gwamon bundles menu
                    session.setCurrentState("gwamon_menu");
                sessionRepo.save(session);
                    return showGwamonBundlesText();
                }
            }
            
            if (paymentMethod < 1 || paymentMethod > 2) {
                return "END Invalid payment method. Please try again.";
            }
            
            // Get selected bundle from session
            Long selectedBundleId = session.getSelectedBundleId();
            if (selectedBundleId == null) {
                return "END Your session has expired. Please dial *154# again to start a new session.";
            }
            
            String paymentMethodName = paymentMethod == 1 ? "Airtime" : "MoMo";
            String bundleDescription = "";
            String confirmation = "";
            
            // Handle Triple Data Promo bundles (IDs 1, 2, 3)
            if (selectedBundleId == 1L) {
                bundleDescription = "200Frw=600MB+300MB(Night)/24hrs";
            } else if (selectedBundleId == 2L) {
                bundleDescription = "500Frw=2GB+1GB (Night)/24hrs";
            } else if (selectedBundleId == 3L) {
                bundleDescription = "1000Frw=2GB+1GB (Night)/7dys";
            } else if (selectedBundleId == 10L) {
                bundleDescription = "100Frw=50Mins+20SMS/24hrs";
            } else if (selectedBundleId == 11L) {
                bundleDescription = "200Frw=250Mins+20SMS/24hrs";
            } else if (selectedBundleId == 12L) {
                bundleDescription = "500Frw=800Mins+20SMS/7Days";
            } else if (selectedBundleId == 13L) {
                bundleDescription = "1000Frw=(120 Mins+1GB) per day /7days";
            } else if (selectedBundleId == 14L) {
                bundleDescription = "2000Frw=4000Mins+100SMS/30 Days";
            } else {
                // Handle database bundles (Gwamon bundles)
            Optional<Bundle> bundleOpt = bundleRepo.findById(selectedBundleId.intValue());
            if (!bundleOpt.isPresent()) {
                return "END Bundle not found. Please dial *154# again to start a new session.";
            }
            
            Bundle selectedBundle = bundleOpt.get();
                bundleDescription = selectedBundle.getUssdDisplayFormat();
            
                // Create purchase record for database bundles
            Purchase purchase = new Purchase();
            purchase.setPhoneNumber(phoneNumber);
            purchase.setPaymentMethod(paymentMethodName);
            purchase.setStatus("completed");
            purchase.setPurchaseDate(new Date());
            purchase.setPurchaseId("PUR-" + System.currentTimeMillis());
            purchase.setBundle(selectedBundle);
            
            purchaseRepo.save(purchase);
            }
            
            // Clean up session
            session.deactivate();
            sessionRepo.save(session);
            
            confirmation = String.format(
                "END Purchase successful!\n%s purchased via %s.\nThank you for using MTN YOLO USSD!",
                bundleDescription,
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
            
            // Handle USSD flow with database session management
            if (text == null || text.isEmpty()) {
                // Initial request - create or get session and show main menu
                return handleInitialRequest(sessionId, phoneNumber);
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
     * Scheduled task to clean up old sessions every 5 minutes
     */
    @Scheduled(fixedRate = 300000) // 5 minutes
    public void cleanupOldSessions() {
        try {
            // Deactivate expired sessions
            int expiredCount = sessionRepo.deactivateExpiredSessions(LocalDateTime.now());
            
            // Delete sessions older than 24 hours
            LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
            int deletedCount = sessionRepo.deleteOldSessions(cutoffTime);
            
            if (expiredCount > 0 || deletedCount > 0) {
                System.out.println("Session cleanup: " + expiredCount + " expired, " + deletedCount + " deleted");
            }
        } catch (Exception e) {
            System.err.println("Error during session cleanup: " + e.getMessage());
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
