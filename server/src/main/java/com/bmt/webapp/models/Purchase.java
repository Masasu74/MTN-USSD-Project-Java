package com.bmt.webapp.models;

import jakarta.persistence.*;
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

    // Bundle information (stored as JSON or separate fields)
    private String bundleName;         // Bundle name
    private int bundlePrice;           // Bundle price
    private String bundleData;         // Bundle data allowance
    private String bundleMinutes;      // Bundle minutes allowance
    private String bundleSms;          // Bundle SMS allowance
    private String bundleValidUntil;   // Bundle validity period
    private boolean bundleIsWeekend;   // Whether bundle is weekend type

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

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public int getBundlePrice() {
        return bundlePrice;
    }

    public void setBundlePrice(int bundlePrice) {
        this.bundlePrice = bundlePrice;
    }

    public String getBundleData() {
        return bundleData;
    }

    public void setBundleData(String bundleData) {
        this.bundleData = bundleData;
    }

    public String getBundleMinutes() {
        return bundleMinutes;
    }

    public void setBundleMinutes(String bundleMinutes) {
        this.bundleMinutes = bundleMinutes;
    }

    public String getBundleSms() {
        return bundleSms;
    }

    public void setBundleSms(String bundleSms) {
        this.bundleSms = bundleSms;
    }

    public String getBundleValidUntil() {
        return bundleValidUntil;
    }

    public void setBundleValidUntil(String bundleValidUntil) {
        this.bundleValidUntil = bundleValidUntil;
    }

    public boolean isBundleIsWeekend() {
        return bundleIsWeekend;
    }

    public void setBundleIsWeekend(boolean bundleIsWeekend) {
        this.bundleIsWeekend = bundleIsWeekend;
    }
}
