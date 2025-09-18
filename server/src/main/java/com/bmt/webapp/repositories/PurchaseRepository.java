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
    @Query("SELECT p FROM Purchase p WHERE p.phoneNumber = :phoneNumber ORDER BY p.purchaseDate DESC")
    List<Purchase> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    
    // Find purchases by status
    @Query("SELECT p FROM Purchase p WHERE p.status = :status")
    List<Purchase> findByStatus(@Param("status") String status, Sort sort);
    
    // Find purchases by payment method
    @Query("SELECT p FROM Purchase p WHERE p.paymentMethod = :paymentMethod")
    List<Purchase> findByPaymentMethod(@Param("paymentMethod") String paymentMethod, Sort sort);
    
    // Find purchases by bundle ID (for foreign key constraint checking)
    @Query("SELECT p FROM Purchase p WHERE p.bundle.id = :bundleId")
    List<Purchase> findByBundleId(@Param("bundleId") int bundleId);
    
    // Find all purchases with proper ordering
    @Query("SELECT p FROM Purchase p ORDER BY p.purchaseDate DESC")
    List<Purchase> findAllPurchases();
}

