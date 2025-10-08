package com.bmt.webapp.controllers;

import com.bmt.webapp.models.Bundle;
import com.bmt.webapp.models.MenuCategory;
import com.bmt.webapp.repositories.BundleRepository;
import com.bmt.webapp.repositories.MenuCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CategoryController - REST API for menu categories
 * Provides endpoints for category management and category-based bundle retrieval
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = {"*"})
public class CategoryController {
    
    @Autowired
    private MenuCategoryRepository categoryRepo;
    
    @Autowired
    private BundleRepository bundleRepo;
    
    /**
     * GET /api/categories - Get all categories
     */
    @GetMapping({"", "/"})
    public ResponseEntity<List<MenuCategory>> getAllCategories() {
        List<MenuCategory> categories = categoryRepo.findByIsActiveTrueOrderByDisplayOrder();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * GET /api/categories/main - Get main categories only (no parent)
     */
    @GetMapping("/main")
    public ResponseEntity<List<MenuCategory>> getMainCategories() {
        List<MenuCategory> categories = categoryRepo.findByIsActiveTrueAndParentIdIsNullOrderByDisplayOrder();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * GET /api/categories/{id} - Get category by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuCategory> getCategoryById(@PathVariable Long id) {
        return categoryRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/categories/{id}/bundles - Get bundles for a category
     */
    @GetMapping("/{id}/bundles")
    public ResponseEntity<List<Bundle>> getBundlesByCategory(@PathVariable Long id) {
        List<Bundle> bundles = bundleRepo.findByCategoryIdAndIsActiveTrueOrderByDisplayOrder(id);
        return ResponseEntity.ok(bundles);
    }
    
    /**
     * GET /api/categories/code/{code} - Get category by code
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<MenuCategory> getCategoryByCode(@PathVariable String code) {
        return categoryRepo.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
