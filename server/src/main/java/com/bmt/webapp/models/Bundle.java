package com.bmt.webapp.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

/**
 * Bundle Entity - Represents MTN YOLO USSD offerings
 * This entity stores bundle information for the USSD application
 * Can be accessed via Frontend, Postman, or USSD code like *154#
 */
@Entity
@Table(name ="mtn_bundles")
public class Bundle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Bundle basic information
    private String name;           // Bundle name (e.g., "Gwamon Bundle Premium")
    private int price;            // Price in FRW
    private int data;             // Data allowance in MB (e.g., 8000 for 8GB)
    private int minutes;          // Minutes allowance (e.g., 800)
    private int sms;              // SMS allowance (e.g., 30)
    private int validUntil;       // Validity period in hours (e.g., 168 for 7 days)
    private boolean isWeekend;    // Whether it's a weekend bundle
    private String status;        // Bundle status (Active, Inactive)
    private Date createdAt;       // When bundle was created
    private Date updatedAt;       // When bundle was last updated

    // One-to-many relationship with purchases
    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Purchase> purchases;

    // Getters and Setters for Bundle entity
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public int getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(int validUntil) {
        this.validUntil = validUntil;
    }

    @JsonProperty("isWeekend")
    public boolean isWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(boolean isWeekend) {
        this.isWeekend = isWeekend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    /**
     * Formats the bundle for USSD display
     * Returns a string like "1500FRW=(8GB+800Mins)+30SMS"
     */
    public String getUssdDisplayFormat() {
        StringBuilder display = new StringBuilder();
        display.append(price).append("FRW=");
        
        // Add data in GB if >= 1000MB, otherwise in MB
        if (data >= 1000) {
            display.append(data / 1000).append("GB");
        } else {
            display.append(data).append("MB");
        }
        
        // Add minutes if > 0
        if (minutes > 0) {
            display.append("+").append(minutes).append("Mins");
        }
        
        // Add SMS if > 0
        if (sms > 0) {
            display.append("+").append(sms).append("SMS");
        }
        
        return display.toString();
    }

    /**
     * Gets the bundle name for USSD display
     * For weekend bundles, returns just the name, for others returns the formatted display
     */
    public String getUssdDisplayName() {
        if (isWeekend) {
            return name; // For weekend bundles, just show the name like "Gwamon' Weekend"
        } else {
            return getUssdDisplayFormat(); // For regular bundles, show the formatted string
        }
    }
}
