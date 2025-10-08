-- =====================================================
-- MTN YOLO USSD System - Complete Data Insertion
-- Run this manually: mysql -u root -P 3307 -h localhost mtn_ussd < INSERT_ALL_DATA.sql
-- =====================================================

USE mtn_ussd;

-- =====================================================
-- 1. INSERT MENU CATEGORIES
-- =====================================================
INSERT INTO menu_categories (id, code, name, display_order, parent_id, is_active, description) VALUES
(1, 'triple_data_promo', 'Triple Data Promo', 1, NULL, TRUE, 'Triple data promotional bundles'),
(2, 'gwamon', 'Gwamon''', 2, NULL, TRUE, 'Gwamon bundle offerings'),
(3, 'yolo_voice', 'YOLO Voice', 3, NULL, TRUE, 'Voice and SMS bundles'),
(4, 'yolo_internet', 'YOLO Internet', 4, NULL, TRUE, 'Internet data bundles'),
(5, 'other_bundles', 'Other Bundles', 5, NULL, TRUE, 'Other bundle offerings'),
(6, 'desade', 'DesaDe', 6, NULL, TRUE, 'DesaDe bundles - valid till second day at 23:59'),
(7, 'balance_check', 'Balance check', 7, NULL, TRUE, 'Check account balance'),
(8, 'foleva', 'FoLeva', 8, NULL, TRUE, 'FoLeva bundles - valid until last MB'),
(9, 'ihereze', 'Ihereze', 9, NULL, TRUE, 'Ihereze loan services'),
(10, 'yolo_star', 'YOLO Star', 10, NULL, TRUE, 'YOLO Star loyalty program'),
(11, 'language', 'Hindura Ururimi(Language)', 11, NULL, TRUE, 'Language settings'),
(12, 'yolo_internet_daily', 'Daily(24hrs)', 1, 4, TRUE, 'Daily internet bundles'),
(13, 'yolo_internet_weekly', 'Weekly (7Days)', 2, 4, TRUE, 'Weekly internet bundles'),
(14, 'yolo_internet_monthly', 'Monthly(30Days)', 3, 4, TRUE, 'Monthly internet bundles'),
(15, 'yolo_internet_hourly', 'Hourly', 5, 4, TRUE, 'Hourly internet bundles'),
(16, 'social_media_bundles', 'Social Media Bundles(24hrs)', 6, 4, TRUE, 'Social media bundles'),
(17, 'whatsapp_bundles', 'Whatsapp', 1, 16, TRUE, 'WhatsApp bundles'),
(18, 'facebook_instagram_bundles', 'Facebook na Instagram', 2, 16, TRUE, 'Facebook and Instagram bundles');

-- =====================================================
-- 2. INSERT BUNDLES
-- =====================================================

-- Triple Data Promo Bundles (Category 1) - IDs 1-3
INSERT INTO bundles (id, category_id, code, name, price, data_mb, night_bonus_data_mb, is_night_bonus, minutes, sms, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(1, 1, 'triple_200', '200Frw Triple Data', 200.00, 600, 300, TRUE, 0, 0, 24, '24hrs', 1, '200Frw=600MB+300MB(Night)/24hrs', TRUE, FALSE),
(2, 1, 'triple_500', '500Frw Triple Data', 500.00, 2048, 1024, TRUE, 0, 0, 24, '24hrs', 2, '500Frw=2GB+1GB (Night)/24hrs', TRUE, FALSE),
(3, 1, 'triple_1000', '1000Frw Triple Data', 1000.00, 2048, 1024, TRUE, 0, 0, 168, '7dys', 3, '1000Frw=2GB+1GB (Night)/7dys', TRUE, FALSE);

-- Gwamon Bundles (Category 2) - IDs 4-6
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(4, 2, 'gwamon_weekend', 'Gwamon Weekend', 1500.00, 8000, 800, 30, 0, FALSE, 168, '7 days', 1, 'Gwamon Weekend', TRUE, TRUE),
(5, 2, 'gwamon_premium', 'Gwamon Premium', 2000.00, 10240, 1000, 50, 0, FALSE, 720, '30 days', 2, '2000FRW=10GB+1000Mins+50SMS', TRUE, FALSE),
(6, 2, 'gwamon_basic', 'Gwamon Basic', 1000.00, 5000, 500, 20, 0, FALSE, 168, '7 days', 3, '1000FRW=5GB+500Mins+20SMS', TRUE, FALSE);

-- YOLO Voice Bundles (Category 3) - IDs 10-14
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(10, 3, 'voice_100', '100Frw Voice', 100.00, 0, 50, 20, 0, FALSE, 24, '24hrs', 1, '100Frw=50Mins+20SMS/24hrs', TRUE, FALSE),
(11, 3, 'voice_200', '200Frw Voice', 200.00, 0, 250, 20, 0, FALSE, 24, '24hrs', 2, '200Frw=250Mins+20SMS/24hrs', TRUE, FALSE),
(12, 3, 'voice_500', '500Frw Voice', 500.00, 0, 800, 20, 0, FALSE, 168, '7Days', 3, '500Frw=800Mins+20SMS/7Days', TRUE, FALSE),
(13, 3, 'voice_1000', '1000Frw Voice Daily', 1000.00, 1024, 120, 0, 0, FALSE, 168, '7days', 4, '1000Frw=(120 Mins+1GB) per day /7days', TRUE, FALSE),
(14, 3, 'voice_2000', '2000Frw Voice', 2000.00, 0, 4000, 100, 0, FALSE, 720, '30 Days', 5, '2000Frw=4000Mins+100SMS/30 Days', TRUE, FALSE);

-- Other Bundles (Category 5) - IDs 20-22
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(20, 5, 'other_100', '100Frw Data', 100.00, 100, 0, 0, 0, FALSE, 24, '24hrs', 1, '100Frw=100MB/24hrs', TRUE, FALSE),
(21, 5, 'other_500', '500Frw Data', 500.00, 500, 0, 0, 0, FALSE, 168, '7Days', 2, '500Frw=500MB/7Days', TRUE, FALSE),
(22, 5, 'other_1000', '1000Frw Data', 1000.00, 800, 0, 0, 0, FALSE, 720, '30Days', 3, '1000Frw=800MB/30Days', TRUE, FALSE);

-- DesaDe Bundles (Category 6) - IDs 30-32
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(30, 6, 'desade_voice', '200Frw DesaDe Voice', 200.00, 0, 200, 200, 0, FALSE, 48, '2 Days', 1, '200Frw=200Mins+200SMS/2 Days', TRUE, FALSE),
(31, 6, 'desade_data', '200Frw DesaDe Data', 200.00, 200, 0, 200, 0, FALSE, 48, '2Days', 2, '200Frw=200MBs+200SMS/2Days', TRUE, FALSE),
(32, 6, 'desade_combo', '200Frw DesaDe Combo', 200.00, 100, 100, 0, 0, FALSE, 48, '2Days', 3, '200Frw=100Mins+100MBs/2Days', TRUE, FALSE);

-- FoLeva Bundles (Category 8) - IDs 40-42
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(40, 8, 'foleva_5000', '5000Frw FoLeva', 5000.00, 10240, 1000, 0, 0, FALSE, 8760, 'Until last MB', 1, '5000Frw=10GB+1000Mins', TRUE, FALSE),
(41, 8, 'foleva_10000', '10000Frw FoLeva', 10000.00, 25600, 2500, 0, 0, FALSE, 8760, 'Until last MB', 2, '10000Frw=25GB+2500Mins', TRUE, FALSE),
(42, 8, 'foleva_20000', '20000Frw FoLeva', 20000.00, 76800, 3000, 0, 0, FALSE, 8760, 'Until last MB', 3, '20000Frw=75GB+3000 Mins', TRUE, FALSE);

-- Social Media Bundles - WhatsApp (Category 17) - ID 50
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(50, 17, 'whatsapp_200', '200Frw WhatsApp', 200.00, 510, 0, 0, 0, FALSE, 24, '24hrs', 1, '200Frw = 510MBs/24hrs', TRUE, FALSE);

-- Social Media Bundles - Facebook/Instagram (Category 18) - ID 51
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(51, 18, 'facebook_instagram_200', '200Frw Facebook/Instagram', 200.00, 810, 0, 0, 0, FALSE, 24, '24hrs', 1, '200Frw = 810MBs/24hrs', TRUE, FALSE);

-- YOLO Internet - Daily Bundles (Category 12) - IDs 60-62
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(60, 12, 'daily_100', '100Frw Daily Data', 100.00, 100, 0, 0, 0, FALSE, 24, '24hrs', 1, '100Frw=100MB', TRUE, FALSE),
(61, 12, 'daily_200', '200Frw Daily Data', 200.00, 420, 0, 30, 0, FALSE, 24, '24hrs', 2, '200Frw=420MB+30SMS', TRUE, FALSE),
(62, 12, 'daily_500', '500Frw Daily Data', 500.00, 1536, 0, 0, 0, FALSE, 24, '24hrs', 3, '500Frw = 1536MB', TRUE, FALSE);

-- YOLO Internet - Weekly Bundles (Category 13) - IDs 70-72
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(70, 13, 'weekly_500', '500Frw Weekly Data', 500.00, 1024, 30, 30, 0, TRUE, 168, 'Icyumweru', 1, '500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru', TRUE, FALSE),
(71, 13, 'weekly_1000_voice', '1000Frw Weekly Voice+Data', 1000.00, 1024, 120, 0, 0, FALSE, 168, 'iminsi 7', 2, '1000Frw=(120Mins+1GB) ku munsi /iminsi 7', TRUE, FALSE),
(72, 13, 'weekly_1000_monthly', '1000Frw Monthly Plan', 1000.00, 30720, 100, 0, 0, FALSE, 720, 'Monthly', 3, '1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)', TRUE, FALSE);

-- YOLO Internet - Monthly Bundles (Category 14) - IDs 80-82
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(80, 14, 'monthly_500', '500Frw Monthly Data', 500.00, 1024, 30, 30, 0, TRUE, 720, 'Icyumweru', 1, '500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru', TRUE, FALSE),
(81, 14, 'monthly_1000_voice', '1000Frw Monthly Voice+Data', 1000.00, 1024, 120, 0, 0, FALSE, 720, 'iminsi 7', 2, '1000Frw=(120Mins+1GB) ku munsi /iminsi 7', TRUE, FALSE),
(82, 14, 'monthly_1000_plan', '1000Frw Monthly Plan', 1000.00, 30720, 100, 0, 0, FALSE, 720, 'Monthly', 3, '1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)', TRUE, FALSE);

-- YOLO Internet - Hourly Bundles (Category 15) - IDs 90-92
INSERT INTO bundles (id, category_id, code, name, price, data_mb, minutes, sms, night_bonus_data_mb, is_night_bonus, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(90, 15, 'hourly_500', '500Frw Hourly Data', 500.00, 1024, 30, 30, 0, TRUE, 1, 'Icyumweru', 1, '500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru', TRUE, FALSE),
(91, 15, 'hourly_1000_voice', '1000Frw Hourly Voice+Data', 1000.00, 1024, 120, 0, 0, FALSE, 1, 'iminsi 7', 2, '1000Frw=(120Mins+1GB) ku munsi /iminsi 7', TRUE, FALSE),
(92, 15, 'hourly_1000_plan', '1000Frw Hourly Plan', 1000.00, 30720, 100, 0, 0, FALSE, 1, 'Monthly', 3, '1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)', TRUE, FALSE);

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================
SELECT 'Menu Categories Inserted:' AS status, COUNT(*) AS count FROM menu_categories;
SELECT 'Bundles Inserted:' AS status, COUNT(*) AS count FROM bundles;
SELECT 'Bundles by Category:' AS status;
SELECT c.name AS category, COUNT(b.id) AS bundle_count 
FROM menu_categories c 
LEFT JOIN bundles b ON b.category_id = c.id 
WHERE c.parent_id IS NULL
GROUP BY c.id, c.name 
ORDER BY c.display_order;
