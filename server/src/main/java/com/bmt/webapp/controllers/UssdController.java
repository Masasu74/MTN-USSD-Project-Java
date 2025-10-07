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
     * Shows Other Bundles menu as plain text
     */
    private String showOtherBundlesText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON Other Bundles\n");
        menu.append("Choose an option:\n\n");
        menu.append("1) 100Frw=100MB/24hrs\n");
        menu.append("2) 500Frw=500MB/7Days\n");
        menu.append("3) 1000Frw=800MB/30Days\n");
        menu.append("4) DesaDe\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows DesaDe menu as plain text
     */
    private String showDesaDeText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON DesaDe\n");
        menu.append("Valid till the second day at 23:59\n\n");
        menu.append("1) 200Frw=200Mins+200SMS/2 Days\n");
        menu.append("2) 200Frw=200MBs+200SMS/2Days\n");
        menu.append("3) 200Frw=100Mins+100MBs/2Days\n");
        menu.append("0) Go back");
        
        return menu.toString();
    }

    /**
     * Shows FoLeva menu as plain text
     */
    private String showFoLevaText() {
        StringBuilder menu = new StringBuilder();
        menu.append("CON FoLeva Bundles\n");
        menu.append("Valid until the last MB\n\n");
        menu.append("1) 5000Frw=10GB+1000Mins\n");
        menu.append("2) 10000Frw=25GB+2500Mins\n");
        menu.append("3) 20000Frw=75GB+3000 Mins\n");
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
                return "END Social Media Bundles - Coming soon!";
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
                return "END 100Frw=100MB - Coming soon!";
            } else if (optionIndex == 2) {
                return "END 200Frw=420MB+30SMS - Coming soon!";
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
                return "END 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru - Coming soon!";
            } else if (optionIndex == 2) {
                return "END 1000Frw=(120Mins+1GB) ku munsi /iminsi 7 - Coming soon!";
            } else if (optionIndex == 3) {
                return "END 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day) - Coming soon!";
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
                return "END 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru - Coming soon!";
            } else if (optionIndex == 2) {
                return "END 1000Frw=(120Mins+1GB) ku munsi /iminsi 7 - Coming soon!";
            } else if (optionIndex == 3) {
                return "END 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day) - Coming soon!";
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
                return "END 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru - Coming soon!";
            } else if (optionIndex == 2) {
                return "END 1000Frw=(120Mins+1GB) ku munsi /iminsi 7 - Coming soon!";
            } else if (optionIndex == 3) {
                return "END 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day) - Coming soon!";
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
                return "END 200Frw - Coming soon!";
            } else if (optionIndex == 2) {
                return "END Airtime - Coming soon!";
            } else if (optionIndex == 3) {
                return "END Gwamon' - Coming soon!";
            } else if (optionIndex == 4) {
                return "END Voice + Internet - Coming soon!";
            } else if (optionIndex == 5) {
                return "END Tira4Me - Coming soon!";
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
                return "END Join YOLO Star - Coming soon!";
            } else if (optionIndex == 2) {
                return "END My YOLO Star Account - Coming soon!";
            } else if (optionIndex == 3) {
                return "END YOLO Star Partners - Coming soon!";
            } else if (optionIndex == 4) {
                return "END Redeem Loyalty Points - Coming soon!";
            } else if (optionIndex == 5) {
                return "END Other info - Coming soon!";
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
            } else if (selectedBundleId == 20L) {
                bundleDescription = "100Frw=100MB/24hrs";
            } else if (selectedBundleId == 21L) {
                bundleDescription = "500Frw=500MB/7Days";
            } else if (selectedBundleId == 22L) {
                bundleDescription = "1000Frw=800MB/30Days";
            } else if (selectedBundleId == 30L) {
                bundleDescription = "200Frw=200Mins+200SMS/2 Days";
            } else if (selectedBundleId == 31L) {
                bundleDescription = "200Frw=200MBs+200SMS/2Days";
            } else if (selectedBundleId == 32L) {
                bundleDescription = "200Frw=100Mins+100MBs/2Days";
            } else if (selectedBundleId == 40L) {
                bundleDescription = "5000Frw=10GB+1000Mins";
            } else if (selectedBundleId == 41L) {
                bundleDescription = "10000Frw=25GB+2500Mins";
            } else if (selectedBundleId == 42L) {
                bundleDescription = "20000Frw=75GB+3000 Mins";
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
