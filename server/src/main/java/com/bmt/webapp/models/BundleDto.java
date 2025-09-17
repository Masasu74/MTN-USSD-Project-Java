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

    @NotEmpty(message = "Data is required")
    private String data;

    @NotEmpty(message = "Minutes is required")
    private String minutes;

    @NotEmpty(message = "SMS is required")
    private String sms;

    @NotEmpty(message = "Valid Until is required")
    private String validUntil;
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
