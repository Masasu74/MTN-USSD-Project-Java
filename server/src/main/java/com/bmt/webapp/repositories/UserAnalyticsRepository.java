package com.bmt.webapp.repositories;

import com.bmt.webapp.models.UserAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for UserAnalytics entity
 * Handles user analytics database operations
 */
@Repository
public interface UserAnalyticsRepository extends JpaRepository<UserAnalytics, Long> {
    
    /**
     * Find analytics by phone number
     */
    Optional<UserAnalytics> findByPhoneNumber(String phoneNumber);
    
    /**
     * Find top users by total purchases
     */
    @Query("SELECT u FROM UserAnalytics u ORDER BY u.totalPurchases DESC")
    List<UserAnalytics> findTopByPurchases();
    
    /**
     * Find top users by total spent
     */
    @Query("SELECT u FROM UserAnalytics u ORDER BY u.totalSpent DESC")
    List<UserAnalytics> findTopBySpent();
    
    /**
     * Count total active users
     */
    @Query("SELECT COUNT(u) FROM UserAnalytics u WHERE u.totalSessions > 0")
    long countActiveUsers();
}
