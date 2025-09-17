package com.bmt.webapp.models;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BundleDto - Data Transfer Object for Bundle operations
 * Used for creating and updating bundles in the USSD application
 * Supports Frontend, Postman, and USSD code like *154#
 */
public class BundleDto {

    @NotEmpty(message = "Bundle name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private int price;

    @NotNull(message = "Data is required")
    @Min(value = 0, message = "Data must be positive")
    private int data;

    @NotNull(message = "Minutes is required")
    @Min(value = 0, message = "Minutes must be positive")
    private int minutes;

    @NotNull(message = "SMS is required")
    @Min(value = 0, message = "SMS must be positive")
    private int sms;

    @NotNull(message = "Valid Until is required")
    @Min(value = 1, message = "Valid Until must be at least 1 hour")
    private int validUntil;
    private boolean isWeekend;

    @NotEmpty(message = "Status is required")
    private String status; // Active, Inactive

    // Getters and Setters for BundleDto
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
}
