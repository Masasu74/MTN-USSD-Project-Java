package com.bmt.webapp.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * UserAnalytics Entity - Aggregate user behavior and statistics
 * Tracks user engagement, purchases, and preferences
 */
@Entity
@Table(name = "user_analytics")
public class UserAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Column(name = "total_sessions")
    private Integer totalSessions = 0;

    @Column(name = "total_purchases")
    private Integer totalPurchases = 0;

    @Column(name = "total_spent", precision = 12, scale = 2)
    private BigDecimal totalSpent = BigDecimal.ZERO;

    @Column(name = "last_session_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSessionAt;

    @Column(name = "last_purchase_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPurchaseAt;

    @Column(name = "favorite_category_id")
    private Long favoriteCategoryId;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(Integer totalSessions) {
        this.totalSessions = totalSessions;
    }

    public Integer getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(Integer totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Date getLastSessionAt() {
        return lastSessionAt;
    }

    public void setLastSessionAt(Date lastSessionAt) {
        this.lastSessionAt = lastSessionAt;
    }

    public Date getLastPurchaseAt() {
        return lastPurchaseAt;
    }

    public void setLastPurchaseAt(Date lastPurchaseAt) {
        this.lastPurchaseAt = lastPurchaseAt;
    }

    public Long getFavoriteCategoryId() {
        return favoriteCategoryId;
    }

    public void setFavoriteCategoryId(Long favoriteCategoryId) {
        this.favoriteCategoryId = favoriteCategoryId;
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
