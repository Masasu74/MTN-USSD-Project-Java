-- =====================================================
-- MTN YOLO USSD System - Seed Data
-- Populated after Hibernate creates tables
-- =====================================================

-- Menu Categories
INSERT INTO menu_categories (code, name, display_order, parent_id, is_active, description) VALUES
('triple_data_promo', 'Triple Data Promo', 1, NULL, TRUE, 'Triple data promotional bundles'),
('gwamon', 'Gwamon''', 2, NULL, TRUE, 'Gwamon bundle offerings'),
('yolo_voice', 'YOLO Voice', 3, NULL, TRUE, 'Voice and SMS bundles'),
('yolo_internet', 'YOLO Internet', 4, NULL, TRUE, 'Internet data bundles'),
('other_bundles', 'Other Bundles', 5, NULL, TRUE, 'Other bundle offerings'),
('desade', 'DesaDe', 6, NULL, TRUE, 'DesaDe bundles'),
('balance_check', 'Balance check', 7, NULL, TRUE, 'Check account balance'),
('foleva', 'FoLeva', 8, NULL, TRUE, 'FoLeva bundles'),
('ihereze', 'Ihereze', 9, NULL, TRUE, 'Ihereze loan services'),
('yolo_star', 'YOLO Star', 10, NULL, TRUE, 'YOLO Star loyalty program');

-- Gwamon Bundles (Category 2)
INSERT INTO bundles (category_id, code, name, price, data_mb, minutes, sms, validity_hours, validity_description, display_order, ussd_display_format, is_active, is_weekend) VALUES
(2, 'gwamon_weekend', 'Gwamon Weekend', 1500.00, 8000, 800, 30, 168, '7 days', 1, 'Gwamon Weekend', TRUE, TRUE),
(2, 'gwamon_premium', 'Gwamon Premium', 2000.00, 10240, 1000, 50, 720, '30 days', 2, '2000FRW=10GB+1000Mins+50SMS', TRUE, FALSE),
(2, 'gwamon_basic', 'Gwamon Basic', 1000.00, 5000, 500, 20, 168, '7 days', 3, '1000FRW=5GB+500Mins+20SMS', TRUE, FALSE);
