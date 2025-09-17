package com.bmt.webapp.repositories;

import com.bmt.webapp.models.Bundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.util.List;

/**
 * BundleRepository - Repository for Bundle entity operations
 * Provides CRUD operations for bundles in the USSD application
 * Supports Frontend, Postman, and USSD code like *154#
 */
public interface BundleRepository extends JpaRepository<Bundle, Integer> {
    // Find bundles by status (Active, Inactive)
    List<Bundle> findByStatus(String status, Sort sort);
    
    // Find weekend bundles
    List<Bundle> findByIsWeekend(boolean isWeekend, Sort sort);
    
    // Find bundles by name (for search functionality)
    List<Bundle> findByNameContainingIgnoreCase(String name, Sort sort);
}
