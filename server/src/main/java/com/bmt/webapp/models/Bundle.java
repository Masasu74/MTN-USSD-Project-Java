package com.bmt.webapp.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Bundle Entity - Represents MTN YOLO USSD bundle offerings
 * Enhanced schema supporting all YOLO USSD categories
 * Can be accessed via Frontend, Postman, or USSD code like *154#
 */
@Entity
@Table(name = "bundles")
public class Bundle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "data_mb")
    private Integer dataMb;

    @Column(name = "minutes")
    private Integer minutes;

    @Column(name = "sms")
    private Integer sms;

    @Column(name = "validity_hours", nullable = false)
    private Integer validityHours;

    @Column(name = "validity_description", length = 100)
    private String validityDescription;

    @Column(name = "is_night_bonus")
    private Boolean isNightBonus;

    @Column(name = "night_bonus_data_mb")
    private Integer nightBonusDataMb;

    @Column(name = "is_weekend")
    private Boolean isWeekend;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(name = "ussd_display_format")
    private String ussdDisplayFormat;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Relationships
    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Purchase> purchases;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDataMb() {
        return dataMb;
    }

    public void setDataMb(Integer dataMb) {
        this.dataMb = dataMb;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSms() {
        return sms;
    }

    public void setSms(Integer sms) {
        this.sms = sms;
    }

    public Integer getValidityHours() {
        return validityHours;
    }

    public void setValidityHours(Integer validityHours) {
        this.validityHours = validityHours;
    }

    public String getValidityDescription() {
        return validityDescription;
    }

    public void setValidityDescription(String validityDescription) {
        this.validityDescription = validityDescription;
    }

    public Boolean getIsNightBonus() {
        return isNightBonus;
    }

    public void setIsNightBonus(Boolean isNightBonus) {
        this.isNightBonus = isNightBonus;
    }

    public Integer getNightBonusDataMb() {
        return nightBonusDataMb;
    }

    public void setNightBonusDataMb(Integer nightBonusDataMb) {
        this.nightBonusDataMb = nightBonusDataMb;
    }

    @JsonProperty("isWeekend")
    public Boolean getIsWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(Boolean isWeekend) {
        this.isWeekend = isWeekend;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getUssdDisplayFormat() {
        return ussdDisplayFormat;
    }

    public void setUssdDisplayFormat(String ussdDisplayFormat) {
        this.ussdDisplayFormat = ussdDisplayFormat;
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

    // Commented out to avoid compilation errors since category field is removed
    // public MenuCategory getCategory() {
    //     return null;
    // }

    // public void setCategory(MenuCategory category) {
    //     // No-op
    // }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    // Legacy compatibility methods
    @Deprecated
    public int getData() {
        return dataMb != null ? dataMb : 0;
    }

    @Deprecated
    public void setData(int data) {
        this.dataMb = data;
    }

    @Deprecated
    public int getValidUntil() {
        return validityHours != null ? validityHours : 0;
    }

    @Deprecated
    public void setValidUntil(int validUntil) {
        this.validityHours = validUntil;
    }

    @Deprecated
    public boolean isWeekend() {
        return isWeekend != null && isWeekend;
    }

    /**
     * Formats the bundle for USSD display
     * Returns a string like "1500FRW=(8GB+800Mins)+30SMS"
     * Uses ussdDisplayFormat if available, otherwise generates from bundle data
     */
    public String getFormattedUssdDisplay() {
        // Use custom format if available
        if (ussdDisplayFormat != null && !ussdDisplayFormat.isEmpty()) {
            return ussdDisplayFormat;
        }
        
        // Generate format from bundle data
        StringBuilder display = new StringBuilder();
        display.append(price.intValue()).append("FRW=");
        
        // Add data in GB if >= 1000MB, otherwise in MB
        if (dataMb != null && dataMb > 0) {
            if (dataMb >= 1000) {
                display.append(dataMb / 1000).append("GB");
            } else {
                display.append(dataMb).append("MB");
            }
        }
        
        // Add minutes if > 0
        if (minutes != null && minutes > 0) {
            if (display.length() > 0 && !display.toString().endsWith("=")) {
                display.append("+");
            }
            display.append(minutes).append("Mins");
        }
        
        // Add SMS if > 0
        if (sms != null && sms > 0) {
            if (display.length() > 0 && !display.toString().endsWith("=")) {
                display.append("+");
            }
            display.append(sms).append("SMS");
        }
        
        return display.toString();
    }

    /**
     * Gets the bundle name for USSD display
     * For weekend bundles, returns just the name, for others returns the formatted display
     */
    public String getUssdDisplayName() {
        if (isWeekend != null && isWeekend) {
            return name; // For weekend bundles, just show the name like "Gwamon' Weekend"
        } else {
            return getFormattedUssdDisplay(); // For regular bundles, show the formatted string
        }
    }
}
