-- Create the MTN YOLO USSD database
CREATE DATABASE IF NOT EXISTS mtn_ussd CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use the database
USE mtn_ussd;

-- Show success message
SELECT 'Database mtn_ussd created successfully!' AS status;
