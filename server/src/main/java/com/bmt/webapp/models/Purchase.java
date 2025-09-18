package com.bmt.webapp.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 * Purchase Entity - Represents bundle purchases made by customers
 * This entity tracks all purchases made through Frontend, Postman, or USSD
 * Used for purchase history and transaction tracking
 */
@Entity
@Table(name = "mtn_purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Purchase information
    private String phoneNumber;        // Customer phone number
    private String paymentMethod;      // Payment method (airtime, momo)
    private String status;             // Purchase status (completed, pending, failed)
    private Date purchaseDate;         // When purchase was made
    private String purchaseId;         // Unique purchase ID (e.g., PUR-1234567890)

    // Foreign key relationship to Bundle
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bundle_id", nullable = false)
    private Bundle bundle;             // Reference to the purchased bundle

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    // Convenience methods to access bundle details
    @JsonProperty("bundleName")
    public String getBundleName() {
        return bundle != null ? bundle.getName() : null;
    }

    @JsonProperty("bundlePrice")
    public int getBundlePrice() {
        return bundle != null ? bundle.getPrice() : 0;
    }

    @JsonProperty("bundleData")
    public int getBundleData() {
        return bundle != null ? bundle.getData() : 0;
    }

    @JsonProperty("bundleMinutes")
    public int getBundleMinutes() {
        return bundle != null ? bundle.getMinutes() : 0;
    }

    @JsonProperty("bundleSms")
    public int getBundleSms() {
        return bundle != null ? bundle.getSms() : 0;
    }

    @JsonProperty("bundleValidUntil")
    public int getBundleValidUntil() {
        return bundle != null ? bundle.getValidUntil() : 0;
    }

    @JsonProperty("bundleIsWeekend")
    public boolean isBundleIsWeekend() {
        return bundle != null && bundle.isWeekend();
    }
}
