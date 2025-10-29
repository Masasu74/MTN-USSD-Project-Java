package com.bmt.webapp.controllers;

import com.bmt.webapp.models.Bundle;
import com.bmt.webapp.models.MenuCategory;
import com.bmt.webapp.models.Purchase;
import com.bmt.webapp.models.UssdSession;
import com.bmt.webapp.repositories.BundleRepository;
import com.bmt.webapp.repositories.MenuCategoryRepository;
import com.bmt.webapp.repositories.PurchaseRepository;
import com.bmt.webapp.repositories.UssdSessionRepository;
import com.bmt.webapp.services.SessionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.xml.bind.annotation.*;
import java.math.BigDecimal;
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
    
    @Autowired
    private MenuCategoryRepository categoryRepo;
    
    @Autowired
    private SessionLogService sessionLogService;

    /**
     * POST /ussd/154 - USSD endpoint for *154# short code
     * Handles USSD session requests from telecom providers
     * 
     * @param request USSD request containing session info and user input
     * @return USSD response with menu or confirmation
     */
    @PostMapping(value = "/154", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String handleUssdRequest154(@RequestParam(required = false) String sessionid,
                                      @RequestParam(required = false) String msidn,
                                      @RequestParam(required = false) String input,
                                      @RequestParam(required = false) String newrequest,
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
                newrequest = extractXmlValue(xmlBody, "newrequest");
            }
            
            System.out.println("USSD Request 154 - Session: " + sessionId + ", Phone: " + phoneNumber + ", Text: " + text + ", NewRequest: " + newrequest);
            
            // Handle USSD flow with database session management
            String response;
            if ("true".equalsIgnoreCase(newrequest)) {
                // New request - always show main menu regardless of input
                response = handleInitialRequest(sessionId, phoneNumber, "154");
                System.out.println("DEBUG: About to log session interaction. SessionLogService is " + (sessionLogService == null ? "NULL" : "initialized"));
                logSessionInteraction(sessionId, phoneNumber, "SESSION_START", "Main Menu", text != null ? text : "", response, false, null);
            } else if (text == null || text.isEmpty()) {
                // Initial request with no input - create or get session and show main menu
                response = handleInitialRequest(sessionId, phoneNumber, "154");
                System.out.println("DEBUG: About to log session interaction. SessionLogService is " + (sessionLogService == null ? "NULL" : "initialized"));
                logSessionInteraction(sessionId, phoneNumber, "SESSION_START", "Main Menu", "", response, false, null);
            } else {
                // User made a selection - handle based on session state
                response = handleUserSelectionText(text, phoneNumber, sessionId);
                // Log will be done inside handleUserSelectionText for specific actions
            }
            
            return response;
            
        } catch (Exception e) {
            System.err.println("Error processing USSD request: " + e.getMessage());
            e.printStackTrace();
            String errorResponse = "END Sorry, an error occurred. Please try again later.";
            try {
                logSessionInteraction(sessionid, msidn, "ERROR", "Error", input, e.getMessage(), false, null);
            } catch (Exception logError) {
                System.err.println("Failed to log error: " + logError.getMessage());
            }
            return errorResponse;
        }
    }

    /**
     * POST /ussd/345 - USSD endpoint for *345# short code
     * Handles USSD session requests from telecom providers
     * 
     * @param request USSD request containing session info and user input
     * @return USSD response with menu or confirmation
     */
    @PostMapping(value = "/345", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String handleUssdRequest345(@RequestParam(required = false) String sessionid,
                                      @RequestParam(required = false) String msidn,
                                      @RequestParam(required = false) String input,
                                      @RequestParam(required = false) String newrequest,
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
                newrequest = extractXmlValue(xmlBody, "newrequest");
            }
            
            System.out.println("USSD Request 345 - Session: " + sessionId + ", Phone: " + phoneNumber + ", Text: " + text + ", NewRequest: " + newrequest);
            
            // Handle USSD flow with database session management
            String response;
            if ("true".equalsIgnoreCase(newrequest)) {
                // New request - always show main menu regardless of input
                response = handleInitialRequest(sessionId, phoneNumber, "345");
                System.out.println("DEBUG: About to log session interaction. SessionLogService is " + (sessionLogService == null ? "NULL" : "initialized"));
                logSessionInteraction(sessionId, phoneNumber, "SESSION_START", "Main Menu", text != null ? text : "", response, false, null);
            } else if (text == null || text.isEmpty()) {
                // Initial request with no input - create or get session and show main menu
                response = handleInitialRequest(sessionId, phoneNumber, "345");
                System.out.println("DEBUG: About to log session interaction. SessionLogService is " + (sessionLogService == null ? "NULL" : "initialized"));
                logSessionInteraction(sessionId, phoneNumber, "SESSION_START", "Main Menu", "", response, false, null);
            } else {
                // User made a selection - handle based on session state
                response = handleUserSelectionText(text, phoneNumber, sessionId);
                // Log will be done inside handleUserSelectionText for specific actions
            }
            
            return response;
            
        } catch (Exception e) {
            System.err.println("Error processing USSD request: " + e.getMessage());
            e.printStackTrace();
            String errorResponse = "END Sorry, an error occurred. Please try again later.";
            try {
                logSessionInteraction(sessionid, msidn, "ERROR", "Error", input, e.getMessage(), false, null);
            } catch (Exception logError) {
                System.err.println("Failed to log error: " + logError.getMessage());
            }
            return errorResponse;
        }
    }

    /**
     * POST /ussd/140 - USSD endpoint for *140# short code
     * Handles USSD session requests from telecom providers
     * 
     * @param request USSD request containing session info and user input
     * @return USSD response with menu or confirmation
     */
    @PostMapping(value = "/140", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String handleUssdRequest140(@RequestParam(required = false) String sessionid,
                                      @RequestParam(required = false) String msidn,
                                      @RequestParam(required = false) String input,
                                      @RequestParam(required = false) String newrequest,
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
                newrequest = extractXmlValue(xmlBody, "newrequest");
            }
            
            System.out.println("USSD Request 140 - Session: " + sessionId + ", Phone: " + phoneNumber + ", Text: " + text + ", NewRequest: " + newrequest);
            
            // Handle USSD flow with database session management
            String response;
            if ("true".equalsIgnoreCase(newrequest)) {
                // New request - always show main menu regardless of input
                response = handleInitialRequest(sessionId, phoneNumber, "140");
                System.out.println("DEBUG: About to log session interaction. SessionLogService is " + (sessionLogService == null ? "NULL" : "initialized"));
                logSessionInteraction(sessionId, phoneNumber, "SESSION_START", "Main Menu", text != null ? text : "", response, false, null);
            } else if (text == null || text.isEmpty()) {
                // Initial request with no input - create or get session and show main menu
                response = handleInitialRequest(sessionId, phoneNumber, "140");
                System.out.println("DEBUG: About to log session interaction. SessionLogService is " + (sessionLogService == null ? "NULL" : "initialized"));
                logSessionInteraction(sessionId, phoneNumber, "SESSION_START", "Main Menu", "", response, false, null);
            } else {
                // User made a selection - handle based on session state
                response = handleUserSelectionText(text, phoneNumber, sessionId);
                // Log will be done inside handleUserSelectionText for specific actions
            }
            
            return response;
            
        } catch (Exception e) {
            System.err.println("Error processing USSD request: " + e.getMessage());
            e.printStackTrace();
            String errorResponse = "END Sorry, an error occurred. Please try again later.";
            try {
                logSessionInteraction(sessionid, msidn, "ERROR", "Error", input, e.getMessage(), false, null);
            } catch (Exception logError) {
                System.err.println("Failed to log error: " + logError.getMessage());
            }
            return errorResponse;
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
     * Shows the main YOLO menu as plain text - DYNAMICALLY from database
     */
    private String showMainMenuText() {
        return showMainMenuText("154");
    }
    
    /**
     * Shows the main YOLO menu as plain text - DYNAMICALLY from database with short code
     */
    private String showMainMenuText(String shortCode) {
        StringBuilder menu = new StringBuilder();
        menu.append("CON YOLO (*").append(shortCode).append("#)\n");
        menu.append("Choose an option:\n\n");
        
        // Fetch main menu categories from database (parent_id is NULL)
        List<MenuCategory> mainCategories = categoryRepo.findByIsActiveTrueAndParentIdIsNullOrderByDisplayOrder();
        
        if (mainCategories.isEmpty()) {
            // Fallback to static menu if database is empty
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
        } else {
            // Build menu dynamically from database
            for (MenuCategory category : mainCategories) {
                // Special handling for Triple Data Promo (display as 99)
                if ("triple_data_promo".equals(category.getCode())) {
                    menu.append("99) ").append(category.getName()).append("\n");
                }
                // Special handling for Gwamon (display as 0)
                else if ("gwamon".equals(category.getCode())) {
                    menu.append("0) ").append(category.getName()).append("\n");
                }
                // Regular options (1-8)
                else {
                    int displayNumber = category.getDisplayOrder() - 1; // Adjust for 99 and 0
                    menu.append(displayNumber).append(") ").append(category.getName()).append("\n");
                }
            }
            menu.append("n) Next");
        }
        
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
                return showYoloInternet();
            } else if (optionIndex == 3) {
                return showOtherBundles();
            } else if (optionIndex == 4) {
                return showDesaDe();
            } else if (optionIndex == 5) {
                return new UssdResponse("Balance check - Coming soon!", "END");
            } else if (optionIndex == 6) {
                return showFoLeva();
            } else if (optionIndex == 7) {
                return showIhereze();
            } else if (optionIndex == 8) {
                return showYoloStar();
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
     * Shows Other Bundles menu
     */
    private UssdResponse showOtherBundles() {
        StringBuilder menu = new StringBuilder();
        menu.append("Other Bundles\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) 100Frw=100MB/24hrs\n");
        menu.append("2) 500Frw=500MB/7Days\n");
        menu.append("3) 1000Frw=800MB/30Days\n");
        menu.append("4) DesaDe\n");
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows DesaDe menu
     */
    private UssdResponse showDesaDe() {
        StringBuilder menu = new StringBuilder();
        menu.append("DesaDe\n");
        menu.append("Valid till the second day at 23:59\n\n");
        menu.append("1) 200Frw=200Mins+200SMS/2 Days\n");
        menu.append("2) 200Frw=200MBs+200SMS/2Days\n");
        menu.append("3) 200Frw=100Mins+100MBs/2Days\n");
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows FoLeva menu
     */
    private UssdResponse showFoLeva() {
        StringBuilder menu = new StringBuilder();
        menu.append("FoLeva Bundles\n");
        menu.append("Valid until the last MB\n\n");
        menu.append("1) 5000Frw=10GB+1000Mins\n");
        menu.append("2) 10000Frw=25GB+2500Mins\n");
        menu.append("3) 20000Frw=75GB+3000 Mins\n");
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows YOLO Internet menu
     */
    private UssdResponse showYoloInternet() {
        StringBuilder menu = new StringBuilder();
        menu.append("YOLO Internet\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) Daily(24hrs)\n");
        menu.append("2) Weekly (7Days)\n");
        menu.append("3) Monthly(30Days)\n");
        menu.append("4) DesaDe\n");
        menu.append("5) Hourly\n");
        menu.append("6) Social Media Bundles(24hrs)\n");
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows Ihereze menu
     */
    private UssdResponse showIhereze() {
        StringBuilder menu = new StringBuilder();
        menu.append("Get loan 200Frw on Ihereze:\n\n");
        menu.append("1. 200Frw\n");
        menu.append("2. Airtime\n");
        menu.append("3. Gwamon'\n");
        menu.append("4. Voice + Internet\n");
        menu.append("5. Tira4Me\n");
        menu.append("1. Loan Statement\n");
        menu.append("2. Outstanding Loan\n");
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows YOLO Star menu
     */
    private UssdResponse showYoloStar() {
        StringBuilder menu = new StringBuilder();
        menu.append("YOLO Star\n");
        menu.append("Choose an option:\n\n");
        menu.append("1. Join YOLO Star\n");
        menu.append("2. My YOLO Star Account\n");
        menu.append("3. YOLO Star Partners\n");
        menu.append("4. Redeem Loyalty Points\n");
        menu.append("5. Other info\n");
        menu.append("0) Go back");
        
        return new UssdResponse(menu.toString(), "CON");
    }

    /**
     * Shows Gwamon bundles menu
     */
    private UssdResponse showGwamonBundles() {
        // Category 2 is Gwamon
        List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(2L);
        
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
                session.setCurrentState("yolo_internet");
                sessionRepo.save(session);
                return showYoloInternetText();
            } else if (optionIndex == 3) {
                session.setCurrentState("other_bundles");
                sessionRepo.save(session);
                return showOtherBundlesText();
            } else if (optionIndex == 4) {
                session.setCurrentState("desade");
                sessionRepo.save(session);
                return showDesaDeText();
            } else if (optionIndex == 5) {
                return "END Balance check - Coming soon!";
            } else if (optionIndex == 6) {
                session.setCurrentState("foleva");
                sessionRepo.save(session);
                return showFoLevaText();
            } else if (optionIndex == 7) {
                session.setCurrentState("ihereze");
                sessionRepo.save(session);
                return showIherezeText();
            } else if (optionIndex == 8) {
                session.setCurrentState("yolo_star");
                sessionRepo.save(session);
                return showYoloStarText();
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
     * Shows Triple Data Promo menu as plain text - DYNAMICALLY from database
     */
    private String showTripleDataPromoText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Triple Data Promo\n");
        menu.append("Choose an option:\n\n");
        
        // Fetch bundles from category 1 (Triple Data Promo)
        List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(1L);
        
        if (bundles.isEmpty()) {
            // Fallback to static if database is empty
            menu.append("1) 200Frw=600MB+300MB(Night)/24hrs\n");
            menu.append("2) 500Frw=2GB+1GB (Night)/24hrs\n");
            menu.append("3) 1000Frw=2GB+1GB (Night)/7dys\n");
        } else {
            // Build menu dynamically
            int index = 1;
        for (Bundle bundle : bundles) {
                menu.append(index++).append(") ").append(bundle.getUssdDisplayFormat()).append("\n");
            }
        }
        
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows YOLO Voice menu as plain text - DYNAMICALLY from database
     */
    private String showYoloVoiceText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON YOLO Voice\n");
        menu.append("Choose an option:\n\n");
        
        // Fetch bundles from category 3 (YOLO Voice) - first 4 items
        List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(3L);
        
        if (bundles.isEmpty()) {
            // Fallback to static
            menu.append("1) 100Frw=50Mins+20SMS/24hrs\n");
            menu.append("2) 200Frw=250Mins+20SMS/24hrs\n");
            menu.append("3) 500Frw=800Mins+20SMS/7Days\n");
            menu.append("4) 1000Frw=(120 Mins+1GB) per day /7days\n");
        } else {
            // Show first 4 bundles
            int count = Math.min(4, bundles.size());
            for (int i = 0; i < count; i++) {
                menu.append(i + 1).append(") ").append(bundles.get(i).getUssdDisplayFormat()).append("\n");
            }
        }
        
        menu.append("n) Next\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows Other Bundles menu as plain text - DYNAMICALLY from database
     */
    private String showOtherBundlesText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Other Bundles\n");
        menu.append("Choose an option:\n\n");
        
        // Fetch bundles from category 5 (Other Bundles)
        List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(5L);
        
        if (bundles.isEmpty()) {
            // Fallback to static
            menu.append("1) 100Frw=100MB/24hrs\n");
            menu.append("2) 500Frw=500MB/7Days\n");
            menu.append("3) 1000Frw=800MB/30Days\n");
        } else {
            // Build menu dynamically
            int index = 1;
            for (Bundle bundle : bundles) {
                menu.append(index++).append(") ").append(bundle.getUssdDisplayFormat()).append("\n");
            }
        }
        
        menu.append("4) DesaDe\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows DesaDe menu as plain text - DYNAMICALLY from database
     */
    private String showDesaDeText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON DesaDe\n");
        menu.append("Valid till the second day at 23:59\n\n");
        
        // Fetch bundles from category 6 (DesaDe)
        List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(6L);
        
        if (bundles.isEmpty()) {
            // Fallback to static
            menu.append("1) 200Frw=200Mins+200SMS/2 Days\n");
            menu.append("2) 200Frw=200MBs+200SMS/2Days\n");
            menu.append("3) 200Frw=100Mins+100MBs/2Days\n");
        } else {
            // Build menu dynamically
            int index = 1;
            for (Bundle bundle : bundles) {
                menu.append(index++).append(") ").append(bundle.getUssdDisplayFormat()).append("\n");
            }
        }
        
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows FoLeva menu as plain text - DYNAMICALLY from database
     */
    private String showFoLevaText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON FoLeva Bundles\n");
        menu.append("Valid until the last MB\n\n");
        
        // Fetch bundles from category 8 (FoLeva)
        List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(8L);
        
        if (bundles.isEmpty()) {
            // Fallback to static
            menu.append("1) 5000Frw=10GB+1000Mins\n");
            menu.append("2) 10000Frw=25GB+2500Mins\n");
            menu.append("3) 20000Frw=75GB+3000 Mins\n");
        } else {
            // Build menu dynamically
            int index = 1;
            for (Bundle bundle : bundles) {
                menu.append(index++).append(") ").append(bundle.getUssdDisplayFormat()).append("\n");
            }
        }
        
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows YOLO Internet menu as plain text
     */
    private String showYoloInternetText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON YOLO Internet\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) Daily(24hrs)\n");
        menu.append("2) Weekly (7Days)\n");
        menu.append("3) Monthly(30Days)\n");
        menu.append("4) DesaDe\n");
        menu.append("5) Hourly\n");
        menu.append("6) Social Media Bundles(24hrs)\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows YOLO Internet Daily bundles as plain text
     */
    private String showYoloInternetDailyText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Daily(24hrs)\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) 100Frw=100MB\n");
        menu.append("2) 200Frw=420MB+30SMS\n");
        menu.append("1. 500Frw = 1536MB\n");
        menu.append("2. Triple Data Promo\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows YOLO Internet Weekly bundles as plain text
     */
    private String showYoloInternetWeeklyText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Weekly (7Days)\n");
        menu.append("Choose an option:\n\n");
        menu.append("1. 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru\n");
        menu.append("2. 1000Frw=(120Mins+1GB) ku munsi /iminsi 7\n");
        menu.append("3) 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)\n");
        menu.append("n) Next\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows YOLO Internet Monthly bundles as plain text
     */
    private String showYoloInternetMonthlyText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Monthly(30Days)\n");
        menu.append("Choose an option:\n\n");
        menu.append("1. 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru\n");
        menu.append("2. 1000Frw=(120Mins+1GB) ku munsi /iminsi 7\n");
        menu.append("3) 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)\n");
        menu.append("n) Next\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows YOLO Internet Hourly bundles as plain text
     */
    private String showYoloInternetHourlyText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Hourly\n");
        menu.append("Choose an option:\n\n");
        menu.append("1. 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru\n");
        menu.append("2. 1000Frw=(120Mins+1GB) ku munsi /iminsi 7\n");
        menu.append("3) 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)\n");
        menu.append("n) Next\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows Social Media Bundles menu as plain text
     */
    private String showSocialMediaBundlesText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Social Media Bundles(24hrs)\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) Whatsapp\n");
        menu.append("2) Facebook na Instagram\n");
        menu.append("0) Gusubira Inyuma");
        
        return menu.toString();
    }

    /**
     * Shows WhatsApp bundles menu as plain text
     */
    private String showWhatsAppBundlesText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Whatsapp\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) 200Frw = 510MBs/24hrs\n");
        menu.append("0) Gusubira Inyuma");
        
        return menu.toString();
    }

    /**
     * Shows Facebook/Instagram bundles menu as plain text
     */
    private String showFacebookInstagramBundlesText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Facebook na Instagram\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) 200Frw = 810MBs/24hrs\n");
        menu.append("0) Gusubira Inyuma");
        
        return menu.toString();
    }

    /**
     * Shows Ihereze menu as plain text
     */
    private String showIherezeText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Get loan 200Frw on Ihereze:\n\n");
        menu.append("1. 200Frw\n");
        menu.append("2. Airtime\n");
        menu.append("3. Gwamon'\n");
        menu.append("4. Voice + Internet\n");
        menu.append("5. Tira4Me\n");
        menu.append("1. Loan Statement\n");
        menu.append("2. Outstanding Loan\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows YOLO Star menu as plain text
     */
    private String showYoloStarText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON YOLO Star\n");
        menu.append("Choose an option:\n\n");
        menu.append("1. Join YOLO Star\n");
        menu.append("2. My YOLO Star Account\n");
        menu.append("3. YOLO Star Partners\n");
        menu.append("4. Redeem Loyalty Points\n");
        menu.append("5. Other info\n");
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
            }
            
            // Fetch bundles from category 1 (Triple Data Promo)
            List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(1L);
            
            if (optionIndex >= 1 && optionIndex <= bundles.size()) {
                // Get the selected bundle
                Bundle selectedBundle = bundles.get(optionIndex - 1);
                
                // Store bundle selection and show payment options
                session.setSelectedBundleId(selectedBundle.getId());
                session.setCurrentState("payment_menu");
                session.setLastInput("triple_data_promo");
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
            }
            
            // Fetch bundles from category 3 (YOLO Voice)
            List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(3L);
            
            // Check if selection is valid (1-4 for first page)
            if (optionIndex >= 1 && optionIndex <= Math.min(4, bundles.size())) {
                Bundle selectedBundle = bundles.get(optionIndex - 1);
                session.setSelectedBundleId(selectedBundle.getId());
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_voice");
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
                // Navigate to DesaDe menu
                session.setCurrentState("desade");
                sessionRepo.save(session);
                return showDesaDeText();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles Other Bundles selection
     */
    private String handleOtherBundlesSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            } else if (optionIndex == 1) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(20L); // 100Frw other bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("other_bundles"); // Remember we came from Other Bundles
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 2) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(21L); // 500Frw other bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("other_bundles"); // Remember we came from Other Bundles
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 3) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(22L); // 1000Frw other bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("other_bundles"); // Remember we came from Other Bundles
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 4) {
                // Navigate to DesaDe menu
                session.setCurrentState("desade");
                sessionRepo.save(session);
                return showDesaDeText();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles DesaDe selection
     */
    private String handleDesaDeSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            } else if (optionIndex == 1) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(30L); // 200Frw DesaDe bundle 1
                session.setCurrentState("payment_menu");
                session.setLastInput("desade"); // Remember we came from DesaDe
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 2) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(31L); // 200Frw DesaDe bundle 2
                session.setCurrentState("payment_menu");
                session.setLastInput("desade"); // Remember we came from DesaDe
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 3) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(32L); // 200Frw DesaDe bundle 3
                session.setCurrentState("payment_menu");
                session.setLastInput("desade"); // Remember we came from DesaDe
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
     * Handles FoLeva selection
     */
    private String handleFoLevaSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            } else if (optionIndex == 1) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(40L); // 5000Frw FoLeva bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("foleva"); // Remember we came from FoLeva
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 2) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(41L); // 10000Frw FoLeva bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("foleva"); // Remember we came from FoLeva
                sessionRepo.save(session);
                return showPaymentMenuText();
            } else if (optionIndex == 3) {
                // Store bundle selection and show payment options
                session.setSelectedBundleId(42L); // 20000Frw FoLeva bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("foleva"); // Remember we came from FoLeva
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
     * Handles YOLO Internet main menu selection
     */
    private String handleYoloInternetSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            } else if (optionIndex == 1) {
                // Navigate to Daily bundles
                session.setCurrentState("yolo_internet_daily");
                sessionRepo.save(session);
                return showYoloInternetDailyText();
            } else if (optionIndex == 2) {
                // Navigate to Weekly bundles
                session.setCurrentState("yolo_internet_weekly");
                sessionRepo.save(session);
                return showYoloInternetWeeklyText();
            } else if (optionIndex == 3) {
                // Navigate to Monthly bundles
                session.setCurrentState("yolo_internet_monthly");
                sessionRepo.save(session);
                return showYoloInternetMonthlyText();
            } else if (optionIndex == 4) {
                // Navigate to DesaDe menu
                session.setCurrentState("desade");
                sessionRepo.save(session);
                return showDesaDeText();
            } else if (optionIndex == 5) {
                // Navigate to Hourly bundles
                session.setCurrentState("yolo_internet_hourly");
                sessionRepo.save(session);
                return showYoloInternetHourlyText();
            } else if (optionIndex == 6) {
                // Navigate to Social Media Bundles menu
                session.setCurrentState("social_media_bundles");
                sessionRepo.save(session);
                return showSocialMediaBundlesText();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles YOLO Internet Daily bundles selection
     */
    private String handleYoloInternetDailySelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to YOLO Internet menu
                session.setCurrentState("yolo_internet");
                sessionRepo.save(session);
                return showYoloInternetText();
            } else if (optionIndex == 1) {
                // 100Frw=100MB bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_daily");
                session.setSelectedBundleId(100L); // Placeholder ID
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 100Frw=100MB via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 2) {
                // 200Frw=420MB+30SMS bundle
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_daily");
                session.setSelectedBundleId(101L); // Placeholder ID
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 200Frw=420MB+30SMS via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else {
                return "END Invalid selection. Please try again!";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles YOLO Internet Weekly bundles selection
     */
    private String handleYoloInternetWeeklySelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            // Handle special text inputs first
            if ("n".equalsIgnoreCase(selection) || "next".equalsIgnoreCase(selection)) {
                return "END Next page - Coming soon!";
            }
            
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to YOLO Internet menu
                session.setCurrentState("yolo_internet");
                sessionRepo.save(session);
                return showYoloInternetText();
            } else if (optionIndex == 1) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_weekly");
                session.setSelectedBundleId(102L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 500Frw=1024MB+30SMS+30Mins via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 2) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_weekly");
                session.setSelectedBundleId(103L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 3) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_weekly");
                session.setSelectedBundleId(104L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 1000Frw=30GB/Monthly via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles YOLO Internet Monthly bundles selection
     */
    private String handleYoloInternetMonthlySelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            // Handle special text inputs first
            if ("n".equalsIgnoreCase(selection) || "next".equalsIgnoreCase(selection)) {
                return "END Next page - Coming soon!";
            }
            
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to YOLO Internet menu
                session.setCurrentState("yolo_internet");
                sessionRepo.save(session);
                return showYoloInternetText();
            } else if (optionIndex == 1) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_monthly");
                session.setSelectedBundleId(105L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 500Frw=1024MB+30SMS+30Mins via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 2) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_monthly");
                session.setSelectedBundleId(106L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 3) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_monthly");
                session.setSelectedBundleId(107L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 1000Frw=30GB/Monthly via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles YOLO Internet Hourly bundles selection
     */
    private String handleYoloInternetHourlySelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            // Handle special text inputs first
            if ("n".equalsIgnoreCase(selection) || "next".equalsIgnoreCase(selection)) {
                return "END Next page - Coming soon!";
            }
            
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to YOLO Internet menu
                session.setCurrentState("yolo_internet");
                sessionRepo.save(session);
                return showYoloInternetText();
            } else if (optionIndex == 1) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_hourly");
                session.setSelectedBundleId(108L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 500Frw=1024MB+30SMS+30Mins via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 2) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_hourly");
                session.setSelectedBundleId(109L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 3) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_internet_hourly");
                session.setSelectedBundleId(110L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 1000Frw=30GB/Monthly via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles Social Media Bundles main menu selection
     */
    private String handleSocialMediaBundlesSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to YOLO Internet menu
                session.setCurrentState("yolo_internet");
                sessionRepo.save(session);
                return showYoloInternetText();
            } else if (optionIndex == 1) {
                // Navigate to WhatsApp bundles
                session.setCurrentState("whatsapp_bundles");
                sessionRepo.save(session);
                return showWhatsAppBundlesText();
            } else if (optionIndex == 2) {
                // Navigate to Facebook/Instagram bundles
                session.setCurrentState("facebook_instagram_bundles");
                sessionRepo.save(session);
                return showFacebookInstagramBundlesText();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles WhatsApp bundles selection
     */
    private String handleWhatsAppBundlesSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to Social Media Bundles menu
                session.setCurrentState("social_media_bundles");
                sessionRepo.save(session);
                return showSocialMediaBundlesText();
            } else if (optionIndex == 1) {
                session.setCurrentState("payment_menu");
                session.setLastInput("whatsapp_bundles");
                session.setSelectedBundleId(111L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 200Frw=510MBs/24hrs via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles Facebook/Instagram bundles selection
     */
    private String handleFacebookInstagramBundlesSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to Social Media Bundles menu
                session.setCurrentState("social_media_bundles");
                sessionRepo.save(session);
                return showSocialMediaBundlesText();
            } else if (optionIndex == 1) {
                session.setCurrentState("payment_menu");
                session.setLastInput("facebook_instagram_bundles");
                session.setSelectedBundleId(112L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 200Frw=810MBs/24hrs via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles Ihereze selection
     */
    private String handleIherezeSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            } else if (optionIndex == 1) {
                session.setCurrentState("payment_menu");
                session.setLastInput("ihereze");
                session.setSelectedBundleId(113L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay 200Frw via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 2) {
                session.setCurrentState("payment_menu");
                session.setLastInput("ihereze");
                session.setSelectedBundleId(114L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay Airtime via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 3) {
                session.setCurrentState("payment_menu");
                session.setLastInput("ihereze");
                session.setSelectedBundleId(115L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay Gwamon' via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 4) {
                session.setCurrentState("payment_menu");
                session.setLastInput("ihereze");
                session.setSelectedBundleId(116L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay Voice+Internet via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 5) {
                session.setCurrentState("payment_menu");
                session.setLastInput("ihereze");
                session.setSelectedBundleId(117L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Pay Tira4Me via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else {
                return "END Invalid selection. Please try again.";
            }
            
        } catch (NumberFormatException e) {
            return "END Invalid selection. Please try again.";
        }
    }

    /**
     * Handles YOLO Star selection
     */
    private String handleYoloStarSelectionText(String selection, String phoneNumber, UssdSession session) {
        try {
            int optionIndex = Integer.parseInt(selection);
            
            if (optionIndex == 0) {
                // Go back to main YOLO menu
                session.setCurrentState("main_menu");
                sessionRepo.save(session);
                return showMainMenuText();
            } else if (optionIndex == 1) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_star");
                session.setSelectedBundleId(118L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Join YOLO Star via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 2) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_star");
                session.setSelectedBundleId(119L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON My YOLO Star Account via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 3) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_star");
                session.setSelectedBundleId(120L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON YOLO Star Partners via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 4) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_star");
                session.setSelectedBundleId(121L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Redeem Loyalty Points via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
            } else if (optionIndex == 5) {
                session.setCurrentState("payment_menu");
                session.setLastInput("yolo_star");
                session.setSelectedBundleId(122L);
                sessionRepo.save(session);
                StringBuilder menu = new StringBuilder();
                menu.append("CON Other info via:\n");
                menu.append("1. Airtime\n");
                menu.append("2. MoMo\n");
                menu.append("0. Go back");
                return menu.toString();
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
        // Category 2 is Gwamon
        List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(2L);
        
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
                logSessionInteraction(session.getSessionId(), phoneNumber, "NAVIGATION_BACK", "Gwamon Menu", selection, "Returned to main menu", false, null);
                return showMainMenuText();
            }
            
            // Category 2 is Gwamon
            List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(2L);
            
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
                
                // Log bundle selection
                logSessionInteraction(
                    session.getSessionId(), 
                    phoneNumber, 
                    "BUNDLE_SELECTED", 
                    "Gwamon Menu", 
                    selection, 
                    "Selected: " + selectedBundle.getUssdDisplayFormat(),
                    false, 
                    selectedBundle.getId()
                );
                
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
            
            // Category 2 is Gwamon
            List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(2L);
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
            purchase.setPurchasedAt(new Date());
            purchase.setCompletedAt(new Date());
            purchase.setPurchaseId("PUR-" + System.currentTimeMillis());
            purchase.setBundleId(selectedBundle.getId());
            purchase.setAmount(selectedBundle.getPrice());
            
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
    private String handleInitialRequest(String sessionId, String phoneNumber, String shortCode) {
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
                    return createNewSessionAndShowMenu(sessionId, phoneNumber, shortCode);
                } else {
                    // Extend existing session and show menu
                    session.extendSession();
                    sessionRepo.save(session);
                    return showMainMenuText();
                }
            } else {
                // Create new session
                return createNewSessionAndShowMenu(sessionId, phoneNumber, shortCode);
            }
        } catch (Exception e) {
            System.err.println("Error handling initial request: " + e.getMessage());
            return "END Sorry, an error occurred. Please try again later.";
        }
    }
    
    /**
     * Creates new session and shows main menu
     */
    private String createNewSessionAndShowMenu(String sessionId, String phoneNumber, String shortCode) {
        UssdSession newSession = new UssdSession(sessionId, phoneNumber, shortCode);
        sessionRepo.save(newSession);
        return showMainMenuText(shortCode);
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
            String response;
            
            if (currentState == null || "main_menu".equals(currentState)) {
                // User is in main YOLO menu - handle option selection
                response = handleMainMenuSelectionText(selection.trim(), phoneNumber, session);
                logSessionInteraction(sessionId, phoneNumber, "MENU_SELECTION", "Main Menu", selection.trim(), response, false, null);
                return response;
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
            } else if ("yolo_internet".equals(currentState)) {
                // User is in YOLO Internet menu - handle selection
                return handleYoloInternetSelectionText(selection.trim(), phoneNumber, session);
            } else if ("yolo_internet_daily".equals(currentState)) {
                // User is in YOLO Internet Daily menu - handle selection
                return handleYoloInternetDailySelectionText(selection.trim(), phoneNumber, session);
            } else if ("yolo_internet_weekly".equals(currentState)) {
                // User is in YOLO Internet Weekly menu - handle selection
                return handleYoloInternetWeeklySelectionText(selection.trim(), phoneNumber, session);
            } else if ("yolo_internet_monthly".equals(currentState)) {
                // User is in YOLO Internet Monthly menu - handle selection
                return handleYoloInternetMonthlySelectionText(selection.trim(), phoneNumber, session);
            } else if ("yolo_internet_hourly".equals(currentState)) {
                // User is in YOLO Internet Hourly menu - handle selection
                return handleYoloInternetHourlySelectionText(selection.trim(), phoneNumber, session);
            } else if ("social_media_bundles".equals(currentState)) {
                // User is in Social Media Bundles menu - handle selection
                return handleSocialMediaBundlesSelectionText(selection.trim(), phoneNumber, session);
            } else if ("whatsapp_bundles".equals(currentState)) {
                // User is in WhatsApp Bundles menu - handle selection
                return handleWhatsAppBundlesSelectionText(selection.trim(), phoneNumber, session);
            } else if ("facebook_instagram_bundles".equals(currentState)) {
                // User is in Facebook/Instagram Bundles menu - handle selection
                return handleFacebookInstagramBundlesSelectionText(selection.trim(), phoneNumber, session);
            } else if ("other_bundles".equals(currentState)) {
                // User is in Other Bundles menu - handle selection
                return handleOtherBundlesSelectionText(selection.trim(), phoneNumber, session);
            } else if ("desade".equals(currentState)) {
                // User is in DesaDe menu - handle selection
                return handleDesaDeSelectionText(selection.trim(), phoneNumber, session);
            } else if ("foleva".equals(currentState)) {
                // User is in FoLeva menu - handle selection
                return handleFoLevaSelectionText(selection.trim(), phoneNumber, session);
            } else if ("ihereze".equals(currentState)) {
                // User is in Ihereze menu - handle selection
                return handleIherezeSelectionText(selection.trim(), phoneNumber, session);
            } else if ("yolo_star".equals(currentState)) {
                // User is in YOLO Star menu - handle selection
                return handleYoloStarSelectionText(selection.trim(), phoneNumber, session);
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
                } else if ("other_bundles".equals(lastInput)) {
                    session.setCurrentState("other_bundles");
                    sessionRepo.save(session);
                    return showOtherBundlesText();
                } else if ("desade".equals(lastInput)) {
                    session.setCurrentState("desade");
                    sessionRepo.save(session);
                    return showDesaDeText();
                } else if ("foleva".equals(lastInput)) {
                    session.setCurrentState("foleva");
                    sessionRepo.save(session);
                    return showFoLevaText();
                } else if ("yolo_internet_daily".equals(lastInput)) {
                    session.setCurrentState("yolo_internet_daily");
                    sessionRepo.save(session);
                    return showYoloInternetDailyText();
                } else if ("yolo_internet_weekly".equals(lastInput)) {
                    session.setCurrentState("yolo_internet_weekly");
                    sessionRepo.save(session);
                    return showYoloInternetWeeklyText();
                } else if ("yolo_internet_monthly".equals(lastInput)) {
                    session.setCurrentState("yolo_internet_monthly");
                    sessionRepo.save(session);
                    return showYoloInternetMonthlyText();
                } else if ("yolo_internet_hourly".equals(lastInput)) {
                    session.setCurrentState("yolo_internet_hourly");
                    sessionRepo.save(session);
                    return showYoloInternetHourlyText();
                } else if ("whatsapp_bundles".equals(lastInput)) {
                    session.setCurrentState("whatsapp_bundles");
                    sessionRepo.save(session);
                    return showWhatsAppBundlesText();
                } else if ("facebook_instagram_bundles".equals(lastInput)) {
                    session.setCurrentState("facebook_instagram_bundles");
                    sessionRepo.save(session);
                    return showFacebookInstagramBundlesText();
                } else if ("ihereze".equals(lastInput)) {
                    session.setCurrentState("ihereze");
                    sessionRepo.save(session);
                    return showIherezeText();
                } else if ("yolo_star".equals(lastInput)) {
                    session.setCurrentState("yolo_star");
                    sessionRepo.save(session);
                    return showYoloStarText();
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
            BigDecimal bundleAmount = BigDecimal.ZERO;
            
            // Fetch bundle from database by ID
            Optional<Bundle> bundleOpt = bundleRepo.findById(selectedBundleId);
            if (bundleOpt.isPresent()) {
                // Bundle exists in database
                Bundle selectedBundle = bundleOpt.get();
                bundleDescription = selectedBundle.getUssdDisplayFormat();
                bundleAmount = selectedBundle.getPrice();
                
                // Create purchase record
                Purchase purchase = new Purchase();
                purchase.setPhoneNumber(phoneNumber);
                purchase.setPaymentMethod(paymentMethodName);
                purchase.setStatus("completed");
                purchase.setPurchasedAt(new Date());
                purchase.setCompletedAt(new Date());
                purchase.setPurchaseId("PUR-" + System.currentTimeMillis());
                purchase.setBundleId(selectedBundle.getId());
                purchase.setAmount(bundleAmount);
                purchase.setSessionId(session.getSessionId());
                
                purchaseRepo.save(purchase);
                
                // Log successful purchase
                logSessionInteraction(
                    session.getSessionId(), 
                    phoneNumber, 
                    "PURCHASE_COMPLETED", 
                    "Payment Menu", 
                    paymentSelection, 
                    "Purchase successful: " + bundleDescription + " via " + paymentMethodName,
                    true, 
                    selectedBundle.getId()
                );
            } else {
                // Placeholder bundle (not in database yet)
                bundleDescription = "Bundle ID: " + selectedBundleId;
                bundleAmount = getPlaceholderBundleAmount(selectedBundleId);
                
                // Create purchase record for placeholder bundle
                Purchase purchase = new Purchase();
                purchase.setPhoneNumber(phoneNumber);
                purchase.setPaymentMethod(paymentMethodName);
                purchase.setStatus("completed");
                purchase.setPurchasedAt(new Date());
                purchase.setCompletedAt(new Date());
                purchase.setPurchaseId("PUR-" + System.currentTimeMillis());
                purchase.setBundleId(selectedBundleId);
                purchase.setAmount(bundleAmount);
                purchase.setSessionId(session.getSessionId());
                
                purchaseRepo.save(purchase);
                
                // Log successful purchase attempt
                logSessionInteraction(
                    session.getSessionId(), 
                    phoneNumber, 
                    "PURCHASE_COMPLETED", 
                    "Payment Menu", 
                    paymentSelection, 
                    "Purchase successful: " + bundleDescription + " via " + paymentMethodName,
                    true, 
                    selectedBundleId
                );
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
            String newrequest = extractXmlValue(xmlRequest, "newrequest");
            String serviceCode = extractXmlValue(xmlRequest, "servicecode");
            
            // Extract short code from service code (e.g., "*154#" -> "154")
            String shortCode = "154"; // Default
            if (serviceCode != null && serviceCode.startsWith("*") && serviceCode.endsWith("#")) {
                shortCode = serviceCode.substring(1, serviceCode.length() - 1);
            }
            
            System.out.println("XML USSD Request - Session: " + sessionId + ", Phone: " + phoneNumber + ", Text: " + text + ", NewRequest: " + newrequest + ", ServiceCode: " + serviceCode);
            
            // Handle USSD flow with database session management
            if ("true".equalsIgnoreCase(newrequest)) {
                // New request - always show main menu regardless of input
                return handleInitialRequest(sessionId, phoneNumber, shortCode);
            } else if (text == null || text.isEmpty()) {
                // Initial request with no input - create or get session and show main menu
                return handleInitialRequest(sessionId, phoneNumber, shortCode);
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
     * Logs a session interaction to the database
     */
    private void logSessionInteraction(String sessionId, String phoneNumber, String action, 
                                       String menuDisplayed, String userInput, String response, 
                                       boolean purchaseCompleted, Long bundleId) {
        sessionLogService.logInteraction(sessionId, phoneNumber, action, menuDisplayed, userInput, response, purchaseCompleted, bundleId);
    }

    /**
     * GET /ussd/test - Simple test endpoint for debugging
     */
    @GetMapping("/test")
    public UssdResponse testUssd() {
        return showMainMenu();
    }

    /**
     * Get the correct amount for placeholder bundles based on bundle ID
     */
    private BigDecimal getPlaceholderBundleAmount(Long bundleId) {
        // Define amounts for placeholder bundles based on their bundle IDs
        switch (bundleId.intValue()) {
            case 102: return new BigDecimal("500.00");   // 500Frw=1024MB+30SMS+30Mins
            case 103: return new BigDecimal("1000.00");  // 1000Frw=(120Mins+1GB)/day for 7days
            case 104: return new BigDecimal("1000.00");  // 1000Frw=30GB/Monthly
            case 105: return new BigDecimal("500.00");   // 500Frw=1024MB+30SMS+30Mins (Monthly)
            case 106: return new BigDecimal("1000.00");  // 1000Frw=(120Mins+1GB)/day for 7days (Monthly)
            case 107: return new BigDecimal("1000.00");  // 1000Frw=30GB/Monthly (Monthly)
            case 108: return new BigDecimal("500.00");   // 500Frw=1024MB+30SMS+30Mins (Hourly)
            case 109: return new BigDecimal("1000.00");  // 1000Frw=(120Mins+1GB)/day for 7days (Hourly)
            case 110: return new BigDecimal("1000.00");  // 1000Frw=30GB/Monthly (Hourly)
            case 111: return new BigDecimal("200.00");   // 200Frw=510MBs/24hrs (WhatsApp)
            case 112: return new BigDecimal("200.00");   // 200Frw=810MBs/24hrs (Facebook/Instagram)
            case 113: return new BigDecimal("200.00");   // 200Frw (Ihereze)
            case 114: return new BigDecimal("0.00");     // Airtime (Ihereze)
            case 115: return new BigDecimal("0.00");     // Gwamon' (Ihereze)
            case 116: return new BigDecimal("0.00");     // Voice+Internet (Ihereze)
            case 117: return new BigDecimal("0.00");     // Tira4Me (Ihereze)
            case 118: return new BigDecimal("0.00");     // Join YOLO Star
            case 119: return new BigDecimal("0.00");     // My YOLO Star Account
            case 120: return new BigDecimal("0.00");     // YOLO Star Partners
            case 121: return new BigDecimal("0.00");     // Redeem Loyalty Points
            case 122: return new BigDecimal("0.00");     // Other info
            case 100: return new BigDecimal("100.00");   // 100Frw=100MB (Daily)
            case 101: return new BigDecimal("200.00");   // 200Frw=420MB+30SMS (Daily)
            default: return new BigDecimal("0.00");      // Unknown placeholder bundle
        }
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
