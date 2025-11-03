package com.bmt.webapp.repositories;

import com.bmt.webapp.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;
import java.util.List;

/**
 * PurchaseRepository - Repository for Purchase entity operations
 * Tracks all bundle purchases made through the application
 * Supports Frontend, Postman, and USSD code like *154#
 */
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    // Find purchases by phone number (with bundle eager fetch)
    @Query("SELECT p FROM Purchase p LEFT JOIN FETCH p.bundle WHERE p.phoneNumber = :phoneNumber ORDER BY p.purchasedAt DESC")
    List<Purchase> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    
    // Find purchases by status (with bundle eager fetch)
    @Query("SELECT p FROM Purchase p LEFT JOIN FETCH p.bundle WHERE p.status = :status")
    List<Purchase> findByStatus(@Param("status") String status, Sort sort);
    
    // Find purchases by payment method (with bundle eager fetch)
    @Query("SELECT p FROM Purchase p LEFT JOIN FETCH p.bundle WHERE p.paymentMethod = :paymentMethod")
    List<Purchase> findByPaymentMethod(@Param("paymentMethod") String paymentMethod, Sort sort);
    
    // Find purchases by bundle ID (for foreign key constraint checking)
    @Query("SELECT p FROM Purchase p LEFT JOIN FETCH p.bundle WHERE p.bundleId = :bundleId")
    List<Purchase> findByBundleId(@Param("bundleId") Long bundleId);
    
    // Find all purchases with proper ordering (with bundle eager fetch)
    @Query("SELECT p FROM Purchase p LEFT JOIN FETCH p.bundle ORDER BY p.purchasedAt DESC")
    List<Purchase> findAllPurchases();
}

