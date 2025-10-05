package com.bmt.webapp.repositories;

import com.bmt.webapp.models.UssdSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for USSD Session management
 */
@Repository
public interface UssdSessionRepository extends JpaRepository<UssdSession, Long> {
    
    /**
     * Find active session by session ID
     */
    @Query("SELECT s FROM UssdSession s WHERE s.sessionId = :sessionId AND s.isActive = true")
    Optional<UssdSession> findActiveSessionBySessionId(@Param("sessionId") String sessionId);
    
    /**
     * Find session by session ID (including inactive)
     */
    Optional<UssdSession> findBySessionId(String sessionId);
    
    /**
     * Find all active sessions for a phone number
     */
    @Query("SELECT s FROM UssdSession s WHERE s.phoneNumber = :phoneNumber AND s.isActive = true")
    List<UssdSession> findActiveSessionsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    
    /**
     * Find expired sessions
     */
    @Query("SELECT s FROM UssdSession s WHERE s.expiresAt < :now AND s.isActive = true")
    List<UssdSession> findExpiredSessions(@Param("now") LocalDateTime now);
    
    /**
     * Deactivate expired sessions
     */
    @Modifying
    @Transactional
    @Query("UPDATE UssdSession s SET s.isActive = false WHERE s.expiresAt < :now AND s.isActive = true")
    int deactivateExpiredSessions(@Param("now") LocalDateTime now);
    
    /**
     * Clean up old sessions (older than 24 hours)
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM UssdSession s WHERE s.createdAt < :cutoffTime")
    int deleteOldSessions(@Param("cutoffTime") LocalDateTime cutoffTime);
}


