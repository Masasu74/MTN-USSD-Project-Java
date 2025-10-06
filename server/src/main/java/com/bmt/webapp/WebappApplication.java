package com.bmt.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * MTN YOLO USSD Application
 * 
 * This is the main Spring Boot application for the MTN YOLO USSD system.
 * 
 * Features:
 * - Bundle management (CRUD operations)
 * - Bundle purchase functionality
 * - Purchase history tracking
 * 
 * Access Methods:
 * - Frontend web interface
 * - Postman API testing
 * - USSD code like *154# (for future USSD integration)
 * 
 * API Endpoints:
 * - GET /api/bundles - Get all bundles
 * - GET /api/bundles/{id} - Get bundle by ID
 * - POST /api/bundles - Create new bundle
 * - PUT /api/bundles/{id} - Update bundle
 * - DELETE /api/bundles/{id} - Delete bundle
 * - POST /api/bundles/purchase - Purchase a bundle
 * - GET /api/bundles/purchases - Get purchase history
 */
@SpringBootApplication
@EnableScheduling
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
		System.out.println("MTN YOLO USSD Application started successfully!");
		System.out.println("Access the API at: http://localhost:8080/api/bundles");
		System.out.println("Frontend should connect to: http://localhost:8080");
	}

}
