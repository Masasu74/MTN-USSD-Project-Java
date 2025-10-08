package com.bmt.webapp.repositories;

import com.bmt.webapp.models.SessionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository for SessionLog entity
 * Handles session logging database operations
 */
@Repository
public interface SessionLogRepository extends JpaRepository<SessionLog, Long> {
    
    /**
     * Find all logs for a session
     */
    List<SessionLog> findBySessionIdOrderByCreatedAtDesc(String sessionId);
    
    /**
     * Find all logs for a phone number
     */
    List<SessionLog> findByPhoneNumberOrderByCreatedAtDesc(String phoneNumber);
    
    /**
     * Find logs by action type
     */
    List<SessionLog> findByActionTypeOrderByCreatedAtDesc(String actionType);
    
    /**
     * Find logs for a purchase
     */
    List<SessionLog> findByPurchaseIdOrderByCreatedAtDesc(String purchaseId);
    
    /**
     * Find failed actions
     */
    List<SessionLog> findBySuccessFalseOrderByCreatedAtDesc();
    
    /**
     * Count logs for a session
     */
    long countBySessionId(String sessionId);
    
    /**
     * Count logs for a phone number within date range
     */
    @Query("SELECT COUNT(s) FROM SessionLog s WHERE s.phoneNumber = :phoneNumber AND s.createdAt BETWEEN :startDate AND :endDate")
    long countByPhoneNumberAndDateRange(@Param("phoneNumber") String phoneNumber, 
                                       @Param("startDate") Date startDate, 
                                       @Param("endDate") Date endDate);
}
