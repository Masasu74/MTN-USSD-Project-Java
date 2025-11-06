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
    @JsonProperty("bundleName")
    public String getBundleName() {
        return bundle != null ? bundle.getName() : null;
    }

    @JsonProperty("bundlePrice")
    public BigDecimal getBundlePrice() {
        return bundle != null ? bundle.getPrice() : BigDecimal.ZERO;
    }

    @JsonProperty("bundleData")
    public Integer getBundleData() {
        return bundle != null ? bundle.getDataMb() : 0;
    }

    @JsonProperty("bundleMinutes")
    public Integer getBundleMinutes() {
        return bundle != null ? bundle.getMinutes() : 0;
    }

    @JsonProperty("bundleSms")
    public Integer getBundleSms() {
        return bundle != null ? bundle.getSms() : 0;
    }

    @JsonProperty("bundleValidUntil")
    public Integer getBundleValidUntil() {
        return bundle != null ? bundle.getValidityHours() : 0;
    }

    @JsonProperty("bundleIsWeekend")
    public Boolean isBundleIsWeekend() {
        return bundle != null && bundle.getIsWeekend() != null && bundle.getIsWeekend();
    }
}
