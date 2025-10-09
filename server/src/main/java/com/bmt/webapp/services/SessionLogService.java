package com.bmt.webapp.services;

import com.bmt.webapp.models.SessionLog;
import com.bmt.webapp.repositories.SessionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Service for handling session logging
 * Uses REQUIRES_NEW propagation to ensure logs are saved in a separate transaction
 */
@Service
public class SessionLogService {
    
    @Autowired
    private SessionLogRepository sessionLogRepo;
    
    /**
     * Logs a session interaction to the database
     * Uses REQUIRES_NEW to create a new transaction that commits independently
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logInteraction(String sessionId, String phoneNumber, String action, 
                              String menuDisplayed, String userInput, String response, 
                              boolean purchaseCompleted, Long bundleId) {
        try {
            SessionLog log = new SessionLog();
            log.setSessionId(sessionId);
            log.setPhoneNumber(phoneNumber);
            log.setActionType(action);
            log.setCurrentState(menuDisplayed);
            log.setUserInput(userInput);
            log.setResponseSent(response);
            log.setSuccess(purchaseCompleted);
            log.setBundleId(bundleId);
            log.setCreatedAt(new Date());
            
            SessionLog savedLog = sessionLogRepo.saveAndFlush(log);
            System.out.println("✓ Session log saved with ID: " + savedLog.getId() + " - " + action + " for session " + sessionId);
        } catch (Exception e) {
            System.err.println("✗ Error saving session log: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
