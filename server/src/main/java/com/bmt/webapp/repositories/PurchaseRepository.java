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
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    // Find purchases by phone number
    List<Purchase> findByPhoneNumber(String phoneNumber, Sort sort);
    
    // Find purchases by status
    List<Purchase> findByStatus(String status, Sort sort);
    
    // Find purchases by payment method
    List<Purchase> findByPaymentMethod(String paymentMethod, Sort sort);
    
    // Find purchases by bundle ID (for foreign key constraint checking)
    @Query("SELECT p FROM Purchase p WHERE p.bundle.id = :bundleId")
    List<Purchase> findByBundleId(@Param("bundleId") int bundleId);
}

