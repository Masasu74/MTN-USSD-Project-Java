package com.bmt.webapp.models;

import jakarta.persistence.*;

import java.util.Date;

/**
 * Bundle Entity - Represents MTN Gwamon Bundle offerings
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
    private String data;          // Data allowance (e.g., "8GB")
    private String minutes;       // Minutes allowance (e.g., "800Mins")
    private String sms;           // SMS allowance (e.g., "30SMS")
    private String validUntil;    // Validity period (e.g., "Sunday 23:59")
    private boolean isWeekend;    // Whether it's a weekend bundle
    private String status;        // Bundle status (Active, Inactive)
    private Date createdAt;       // When bundle was created
    private Date updatedAt;       // When bundle was last updated

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public void setWeekend(boolean weekend) {
        isWeekend = weekend;
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
}
