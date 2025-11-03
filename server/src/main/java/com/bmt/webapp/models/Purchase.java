package com.bmt.webapp.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Purchase Entity - Represents bundle purchases made by customers
 * Enhanced schema with comprehensive tracking and logging
 * Tracks all purchases made through Frontend, Postman, or USSD
 */
@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_id", nullable = false, unique = true, length = 50)
    private String purchaseId;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "bundle_id", nullable = false)
    private Long bundleId;

    @Column(name = "bundle_snapshot", columnDefinition = "JSON")
    private String bundleSnapshot;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 50)
    private String status = "pending";

    @Column(name = "transaction_reference", length = 100)
    private String transactionReference;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "purchased_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchasedAt;

    @Column(name = "completed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Foreign key relationship to Bundle
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bundle_id", insertable = false, updatable = false)
    private Bundle bundle;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
        if (purchasedAt == null) {
            purchasedAt = new Date();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getBundleId() {
        return bundleId;
    }

    public void setBundleId(Long bundleId) {
        this.bundleId = bundleId;
    }

    public String getBundleSnapshot() {
        return bundleSnapshot;
    }

    public void setBundleSnapshot(String bundleSnapshot) {
        this.bundleSnapshot = bundleSnapshot;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(Date purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    // Legacy compatibility
    @Deprecated
    public Date getPurchaseDate() {
        return purchasedAt;
    }

    @Deprecated
    public void setPurchaseDate(Date purchaseDate) {
        this.purchasedAt = purchaseDate;
    }

    // Convenience methods to access bundle details
    // Uses bundle relationship if available, otherwise falls back to bundleSnapshot
    @JsonProperty("bundleName")
    public String getBundleName() {
        if (bundle != null) {
            return bundle.getName();
        }
        // Fallback to bundleSnapshot if bundle is null
        return getBundlePropertyFromSnapshot("name");
    }

    @JsonProperty("bundlePrice")
    public BigDecimal getBundlePrice() {
        if (bundle != null) {
            return bundle.getPrice();
        }
        // Fallback to amount if bundleSnapshot doesn't have price
        if (amount != null) {
            return amount;
        }
        // Try to get from bundleSnapshot
        String priceStr = getBundlePropertyFromSnapshot("price");
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                return new BigDecimal(priceStr);
            } catch (Exception e) {
                // Ignore
            }
        }
        return BigDecimal.ZERO;
    }

    @JsonProperty("bundleData")
    public Integer getBundleData() {
        if (bundle != null) {
            return bundle.getDataMb();
        }
        // Fallback to bundleSnapshot
        String dataStr = getBundlePropertyFromSnapshot("dataMb");
        if (dataStr == null || dataStr.isEmpty()) {
            dataStr = getBundlePropertyFromSnapshot("data"); // Legacy field
        }
        if (dataStr != null && !dataStr.isEmpty()) {
            try {
                return Integer.parseInt(dataStr);
            } catch (Exception e) {
                // Ignore
            }
        }
        return 0;
    }

    @JsonProperty("bundleMinutes")
    public Integer getBundleMinutes() {
        if (bundle != null) {
            return bundle.getMinutes();
        }
        // Fallback to bundleSnapshot
        String minutesStr = getBundlePropertyFromSnapshot("minutes");
        if (minutesStr != null && !minutesStr.isEmpty()) {
            try {
                return Integer.parseInt(minutesStr);
            } catch (Exception e) {
                // Ignore
            }
        }
        return 0;
    }

    @JsonProperty("bundleSms")
    public Integer getBundleSms() {
        if (bundle != null) {
            return bundle.getSms();
        }
        // Fallback to bundleSnapshot
        String smsStr = getBundlePropertyFromSnapshot("sms");
        if (smsStr != null && !smsStr.isEmpty()) {
            try {
                return Integer.parseInt(smsStr);
            } catch (Exception e) {
                // Ignore
            }
        }
        return 0;
    }

    @JsonProperty("bundleValidUntil")
    public Integer getBundleValidUntil() {
        if (bundle != null) {
            return bundle.getValidityHours();
        }
        // Fallback to bundleSnapshot
        String validStr = getBundlePropertyFromSnapshot("validityHours");
        if (validStr == null || validStr.isEmpty()) {
            validStr = getBundlePropertyFromSnapshot("validUntil"); // Legacy field
        }
        if (validStr != null && !validStr.isEmpty()) {
            try {
                return Integer.parseInt(validStr);
            } catch (Exception e) {
                // Ignore
            }
        }
        return 0;
    }

    @JsonProperty("bundleIsWeekend")
    public Boolean isBundleIsWeekend() {
        if (bundle != null && bundle.getIsWeekend() != null) {
            return bundle.getIsWeekend();
        }
        // Fallback to bundleSnapshot
        String isWeekendStr = getBundlePropertyFromSnapshot("isWeekend");
        if (isWeekendStr != null && !isWeekendStr.isEmpty()) {
            return Boolean.parseBoolean(isWeekendStr);
        }
        return false;
    }
    
    /**
     * Helper method to extract a property from bundleSnapshot JSON
     */
    private String getBundlePropertyFromSnapshot(String propertyName) {
        if (bundleSnapshot == null || bundleSnapshot.trim().isEmpty()) {
            return null;
        }
        try {
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode jsonNode = objectMapper.readTree(bundleSnapshot);
            com.fasterxml.jackson.databind.JsonNode propertyNode = jsonNode.get(propertyName);
            if (propertyNode != null) {
                if (propertyNode.isTextual()) {
                    return propertyNode.asText();
                } else {
                    return propertyNode.asText(); // Will convert number to string
                }
            }
        } catch (Exception e) {
            // Ignore parsing errors
        }
        return null;
    }
}
