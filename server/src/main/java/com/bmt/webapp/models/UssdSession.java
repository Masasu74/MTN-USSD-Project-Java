package com.bmt.webapp.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * USSD Session model for tracking user sessions and state
 * Enhanced with category tracking and navigation history
 */
@Entity
@Table(name = "ussd_sessions")
public class UssdSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "session_id", unique = true, nullable = false)
    private String sessionId;
    
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;
    
    @Column(name = "current_state", length = 100)
    private String currentState; // main_menu, payment_menu, etc.
    
    @Column(name = "current_category_id")
    private Long currentCategoryId;
    
    @Column(name = "selected_bundle_id")
    private Long selectedBundleId;
    
    @Column(name = "last_input")
    private String lastInput;
    
    @Column(name = "navigation_path", columnDefinition = "TEXT")
    private String navigationPath; // JSON array of navigation history
    
    @Column(name = "session_data", columnDefinition = "JSON")
    private String sessionData; // Additional session data
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    // Constructors
    public UssdSession() {}
    
    public UssdSession(String sessionId, String phoneNumber) {
        this.sessionId = sessionId;
        this.phoneNumber = phoneNumber;
        this.currentState = "main_menu";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusMinutes(10); // 10 minutes timeout
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getCurrentState() { return currentState; }
    public void setCurrentState(String currentState) { 
        this.currentState = currentState;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Long getCurrentCategoryId() { return currentCategoryId; }
    public void setCurrentCategoryId(Long currentCategoryId) { 
        this.currentCategoryId = currentCategoryId;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Long getSelectedBundleId() { return selectedBundleId; }
    public void setSelectedBundleId(Long selectedBundleId) { 
        this.selectedBundleId = selectedBundleId;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getLastInput() { return lastInput; }
    public void setLastInput(String lastInput) { 
        this.lastInput = lastInput;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getNavigationPath() { return navigationPath; }
    public void setNavigationPath(String navigationPath) { this.navigationPath = navigationPath; }
    
    public String getSessionData() { return sessionData; }
    public void setSessionData(String sessionData) { this.sessionData = sessionData; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    // Helper methods
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
    
    public void extendSession() {
        this.expiresAt = LocalDateTime.now().plusMinutes(10);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }
}


