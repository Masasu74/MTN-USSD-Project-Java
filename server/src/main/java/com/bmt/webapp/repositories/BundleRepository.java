package com.bmt.webapp.repositories;

import com.bmt.webapp.models.Bundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

/**
 * BundleRepository - Repository for Bundle entity operations
 * Enhanced for YOLO USSD system with category support
 * Supports Frontend, Postman, and USSD code like *154#
 */
public interface BundleRepository extends JpaRepository<Bundle, Long> {
    
    // Find bundles by active status
    List<Bundle> findByIsActive(Boolean isActive, Sort sort);
    
    // Find active bundles ordered by ID descending (for USSD menu)
    List<Bundle> findByIsActiveTrueOrderByIdDesc();
    
    // Find bundles by category
    List<Bundle> findByCategoryIdAndIsActiveTrue(Long categoryId, Sort sort);
    
    // Find bundles by category ordered by display order
    List<Bundle> findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(Long categoryId);
    
    // Find weekend bundles
    List<Bundle> findByIsWeekendAndIsActiveTrue(Boolean isWeekend, Sort sort);
    
    // Find bundles by name (for search functionality)
    List<Bundle> findByNameContainingIgnoreCaseAndIsActiveTrue(String name, Sort sort);
    
    // Find bundle by code
    Optional<Bundle> findByCode(String code);
    
    // Count active bundles in a category
    @Query("SELECT COUNT(b) FROM Bundle b WHERE b.categoryId = :categoryId AND b.isActive = true")
    long countActiveBundlesByCategory(@Param("categoryId") Long categoryId);
    
    // Legacy compatibility - find by old ID type
    @Query("SELECT b FROM Bundle b WHERE b.id = :id")
    Optional<Bundle> findByIdAsInteger(@Param("id") Integer id);
}
