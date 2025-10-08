package com.bmt.webapp.repositories;

import com.bmt.webapp.models.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for MenuCategory entity
 * Handles menu category database operations
 */
@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    
    /**
     * Find category by code
     */
    Optional<MenuCategory> findByCode(String code);
    
    /**
     * Find all active categories
     */
    List<MenuCategory> findByIsActiveTrueOrderByDisplayOrder();
    
    /**
     * Find all active main categories (no parent)
     */
    List<MenuCategory> findByIsActiveTrueAndParentIdIsNullOrderByDisplayOrder();
    
    /**
     * Find all active subcategories of a parent
     */
    List<MenuCategory> findByIsActiveTrueAndParentIdOrderByDisplayOrder(Long parentId);
    
    /**
     * Count active categories
     */
    @Query("SELECT COUNT(m) FROM MenuCategory m WHERE m.isActive = true")
    long countActiveCategories();
}
