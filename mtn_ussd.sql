-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 10, 2025 at 02:16 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mtn_ussd`
--

-- --------------------------------------------------------

--
-- Table structure for table `bundles`
--

CREATE TABLE `bundles` (
  `data_mb` int(11) DEFAULT NULL,
  `display_order` int(11) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_night_bonus` bit(1) DEFAULT NULL,
  `is_weekend` bit(1) DEFAULT NULL,
  `minutes` int(11) DEFAULT NULL,
  `night_bonus_data_mb` int(11) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `sms` int(11) DEFAULT NULL,
  `validity_hours` int(11) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `code` varchar(50) NOT NULL,
  `validity_description` varchar(100) DEFAULT NULL,
  `name` varchar(200) NOT NULL,
  `description` text DEFAULT NULL,
  `ussd_display_format` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `bundles`
--

INSERT INTO `bundles` (`data_mb`, `display_order`, `is_active`, `is_night_bonus`, `is_weekend`, `minutes`, `night_bonus_data_mb`, `price`, `sms`, `validity_hours`, `category_id`, `created_at`, `id`, `updated_at`, `code`, `validity_description`, `name`, `description`, `ussd_display_format`) VALUES
(600, 1, b'1', b'1', b'0', 0, 300, 200.00, 0, 24, 1, NULL, 1, NULL, 'triple_200', '24hrs', '200Frw Triple Data', NULL, '200Frw=600MB+300MB(Night)/24hrs'),
(2048, 2, b'1', b'1', b'0', 0, 1024, 500.00, 0, 24, 1, NULL, 2, NULL, 'triple_500', '24hrs', '500Frw Triple Data', NULL, '500Frw=2GB+1GB (Night)/24hrs'),
(2048, 3, b'1', b'1', b'0', 0, 1024, 1000.00, 0, 168, 1, NULL, 3, NULL, 'triple_1000', '7dys', '1000Frw Triple Data', NULL, '1000Frw=2GB+1GB (Night)/7dys'),
(8000, 1, b'1', b'0', b'1', 800, 0, 1500.00, 30, 168, 2, NULL, 4, NULL, 'gwamon_weekend', '7 days', 'Gwamon Weekend', NULL, 'Gwamon Weekend'),
(10240, 2, b'1', b'0', b'0', 1000, 0, 2000.00, 50, 720, 2, NULL, 5, NULL, 'gwamon_premium', '30 days', 'Gwamon Premium', NULL, '2000FRW=10GB+1000Mins+50SMS'),
(5000, 3, b'1', b'0', b'0', 500, 0, 1000.00, 20, 168, 2, NULL, 6, NULL, 'gwamon_basic', '7 days', 'Gwamon Basic', NULL, '1000FRW=5GB+500Mins+20SMS'),
(0, 1, b'1', b'0', b'0', 50, 0, 100.00, 20, 24, 3, NULL, 10, NULL, 'voice_100', '24hrs', '100Frw Voice', NULL, '100Frw=50Mins+20SMS/24hrs'),
(0, 2, b'1', b'0', b'0', 250, 0, 200.00, 20, 24, 3, NULL, 11, NULL, 'voice_200', '24hrs', '200Frw Voice', NULL, '200Frw=250Mins+20SMS/24hrs'),
(0, 3, b'1', b'0', b'0', 800, 0, 500.00, 20, 168, 3, NULL, 12, NULL, 'voice_500', '7Days', '500Frw Voice', NULL, '500Frw=800Mins+20SMS/7Days'),
(1024, 4, b'1', b'0', b'0', 120, 0, 1000.00, 0, 168, 3, NULL, 13, NULL, 'voice_1000', '7days', '1000Frw Voice Daily', NULL, '1000Frw=(120 Mins+1GB) per day /7days'),
(0, 5, b'1', b'0', b'0', 4000, 0, 2000.00, 100, 720, 3, NULL, 14, NULL, 'voice_2000', '30 Days', '2000Frw Voice', NULL, '2000Frw=4000Mins+100SMS/30 Days'),
(100, 1, b'1', b'0', b'0', 0, 0, 100.00, 0, 24, 5, NULL, 20, NULL, 'other_100', '24hrs', '100Frw Data', NULL, '100Frw=100MB/24hrs'),
(500, 2, b'1', b'0', b'0', 0, 0, 500.00, 0, 168, 5, NULL, 21, NULL, 'other_500', '7Days', '500Frw Data', NULL, '500Frw=500MB/7Days'),
(800, 3, b'1', b'0', b'0', 0, 0, 1000.00, 0, 720, 5, NULL, 22, NULL, 'other_1000', '30Days', '1000Frw Data', NULL, '1000Frw=800MB/30Days'),
(0, 1, b'1', b'0', b'0', 200, 0, 200.00, 200, 48, 6, NULL, 30, NULL, 'desade_voice', '2 Days', '200Frw DesaDe Voice', NULL, '200Frw=200Mins+200SMS/2 Days'),
(200, 2, b'1', b'0', b'0', 0, 0, 200.00, 200, 48, 6, NULL, 31, NULL, 'desade_data', '2Days', '200Frw DesaDe Data', NULL, '200Frw=200MBs+200SMS/2Days'),
(100, 3, b'1', b'0', b'0', 100, 0, 200.00, 0, 48, 6, NULL, 32, NULL, 'desade_combo', '2Days', '200Frw DesaDe Combo', NULL, '200Frw=100Mins+100MBs/2Days'),
(10240, 1, b'1', b'0', b'0', 1000, 0, 5000.00, 0, 8760, 8, NULL, 40, NULL, 'foleva_5000', 'Until last MB', '5000Frw FoLeva', NULL, '5000Frw=10GB+1000Mins'),
(25600, 2, b'1', b'0', b'0', 2500, 0, 10000.00, 0, 8760, 8, NULL, 41, NULL, 'foleva_10000', 'Until last MB', '10000Frw FoLeva', NULL, '10000Frw=25GB+2500Mins'),
(76800, 3, b'1', b'0', b'0', 3000, 0, 20000.00, 0, 8760, 8, NULL, 42, NULL, 'foleva_20000', 'Until last MB', '20000Frw FoLeva', NULL, '20000Frw=75GB+3000 Mins'),
(510, 1, b'1', b'0', b'0', 0, 0, 200.00, 0, 24, 17, NULL, 50, NULL, 'whatsapp_200', '24hrs', '200Frw WhatsApp', NULL, '200Frw = 510MBs/24hrs'),
(810, 1, b'1', b'0', b'0', 0, 0, 200.00, 0, 24, 18, NULL, 51, NULL, 'facebook_instagram_200', '24hrs', '200Frw Facebook/Instagram', NULL, '200Frw = 810MBs/24hrs'),
(100, 1, b'1', b'0', b'0', 0, 0, 100.00, 0, 24, 12, NULL, 60, NULL, 'daily_100', '24hrs', '100Frw Daily Data', NULL, '100Frw=100MB'),
(420, 2, b'1', b'0', b'0', 0, 0, 200.00, 30, 24, 12, NULL, 61, NULL, 'daily_200', '24hrs', '200Frw Daily Data', NULL, '200Frw=420MB+30SMS'),
(1536, 3, b'1', b'0', b'0', 0, 0, 500.00, 0, 24, 12, NULL, 62, NULL, 'daily_500', '24hrs', '500Frw Daily Data', NULL, '500Frw = 1536MB'),
(1024, 1, b'1', b'1', b'0', 30, 0, 500.00, 30, 168, 13, NULL, 70, NULL, 'weekly_500', 'Icyumweru', '500Frw Weekly Data', NULL, '500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru'),
(1024, 2, b'1', b'0', b'0', 120, 0, 1000.00, 0, 168, 13, NULL, 71, NULL, 'weekly_1000_voice', 'iminsi 7', '1000Frw Weekly Voice+Data', NULL, '1000Frw=(120Mins+1GB) ku munsi /iminsi 7'),
(30720, 3, b'1', b'0', b'0', 100, 0, 1000.00, 0, 720, 13, NULL, 72, NULL, 'weekly_1000_monthly', 'Monthly', '1000Frw Monthly Plan', NULL, '1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)'),
(1024, 1, b'1', b'1', b'0', 30, 0, 500.00, 30, 720, 14, NULL, 80, NULL, 'monthly_500', 'Icyumweru', '500Frw Monthly Data', NULL, '500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru'),
(1024, 2, b'1', b'0', b'0', 120, 0, 1000.00, 0, 720, 14, NULL, 81, NULL, 'monthly_1000_voice', 'iminsi 7', '1000Frw Monthly Voice+Data', NULL, '1000Frw=(120Mins+1GB) ku munsi /iminsi 7'),
(30720, 3, b'1', b'0', b'0', 100, 0, 1000.00, 0, 720, 14, NULL, 82, NULL, 'monthly_1000_plan', 'Monthly', '1000Frw Monthly Plan', NULL, '1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)'),
(1024, 1, b'1', b'1', b'0', 30, 0, 500.00, 30, 1, 15, NULL, 90, NULL, 'hourly_500', 'Icyumweru', '500Frw Hourly Data', NULL, '500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru'),
(1024, 2, b'1', b'0', b'0', 120, 0, 1000.00, 0, 1, 15, NULL, 91, NULL, 'hourly_1000_voice', 'iminsi 7', '1000Frw Hourly Voice+Data', NULL, '1000Frw=(120Mins+1GB) ku munsi /iminsi 7'),
(30720, 3, b'1', b'0', b'0', 100, 0, 1000.00, 0, 1, 15, NULL, 92, NULL, 'hourly_1000_plan', 'Monthly', '1000Frw Hourly Plan', NULL, '1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)');

-- --------------------------------------------------------

--
-- Table structure for table `menu_categories`
--

CREATE TABLE `menu_categories` (
  `display_order` int(11) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `code` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `menu_categories`
--

INSERT INTO `menu_categories` (`display_order`, `is_active`, `created_at`, `id`, `parent_id`, `updated_at`, `code`, `name`, `description`) VALUES
(1, b'1', NULL, 1, NULL, NULL, 'triple_data_promo', 'Triple Data Promo', 'Triple data promotional bundles'),
(2, b'1', NULL, 2, NULL, NULL, 'gwamon', 'Gwamon\'', 'Gwamon bundle offerings'),
(3, b'1', NULL, 3, NULL, NULL, 'yolo_voice', 'YOLO Voice', 'Voice and SMS bundles'),
(4, b'1', NULL, 4, NULL, NULL, 'yolo_internet', 'YOLO Internet', 'Internet data bundles'),
(5, b'1', NULL, 5, NULL, NULL, 'other_bundles', 'Other Bundles', 'Other bundle offerings'),
(6, b'1', NULL, 6, NULL, NULL, 'desade', 'DesaDe', 'DesaDe bundles - valid till second day at 23:59'),
(7, b'1', NULL, 7, NULL, NULL, 'balance_check', 'Balance check', 'Check account balance'),
(8, b'1', NULL, 8, NULL, NULL, 'foleva', 'FoLeva', 'FoLeva bundles - valid until last MB'),
(9, b'1', NULL, 9, NULL, NULL, 'ihereze', 'Ihereze', 'Ihereze loan services'),
(10, b'1', NULL, 10, NULL, NULL, 'yolo_star', 'YOLO Star', 'YOLO Star loyalty program'),
(11, b'1', NULL, 11, NULL, NULL, 'language', 'Hindura Ururimi(Language)', 'Language settings'),
(1, b'1', NULL, 12, 4, NULL, 'yolo_internet_daily', 'Daily(24hrs)', 'Daily internet bundles'),
(2, b'1', NULL, 13, 4, NULL, 'yolo_internet_weekly', 'Weekly (7Days)', 'Weekly internet bundles'),
(3, b'1', NULL, 14, 4, NULL, 'yolo_internet_monthly', 'Monthly(30Days)', 'Monthly internet bundles'),
(5, b'1', NULL, 15, 4, NULL, 'yolo_internet_hourly', 'Hourly', 'Hourly internet bundles'),
(6, b'1', NULL, 16, 4, NULL, 'social_media_bundles', 'Social Media Bundles(24hrs)', 'Social media bundles'),
(1, b'1', NULL, 17, 16, NULL, 'whatsapp_bundles', 'Whatsapp', 'WhatsApp bundles'),
(2, b'1', NULL, 18, 16, NULL, 'facebook_instagram_bundles', 'Facebook na Instagram', 'Facebook and Instagram bundles');

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `amount` decimal(10,2) NOT NULL,
  `bundle_id` bigint(20) NOT NULL,
  `completed_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `purchased_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `phone_number` varchar(20) NOT NULL,
  `payment_method` varchar(50) NOT NULL,
  `purchase_id` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  `transaction_reference` varchar(100) DEFAULT NULL,
  `bundle_snapshot` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`bundle_snapshot`)),
  `error_message` text DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`amount`, `bundle_id`, `completed_at`, `created_at`, `id`, `purchased_at`, `updated_at`, `phone_number`, `payment_method`, `purchase_id`, `status`, `transaction_reference`, `bundle_snapshot`, `error_message`, `session_id`) VALUES
(200.00, 1, '2025-10-08 12:19:30.000000', '2025-10-08 12:19:30.000000', 1, '2025-10-08 12:19:30.000000', '2025-10-08 12:19:30.000000', '0783876662', 'Airtime', 'PUR-1759918770341', 'completed', NULL, NULL, NULL, NULL),
(500.00, 21, '2025-10-08 15:31:04.000000', '2025-10-08 15:31:04.000000', 2, '2025-10-08 15:31:04.000000', '2025-10-08 15:31:04.000000', '233241234567', 'Airtime', 'PUR-1759930264589', 'completed', NULL, NULL, NULL, 'test_session_3230'),
(2000.00, 5, '2025-10-09 12:17:53.000000', '2025-10-09 12:17:53.000000', 3, '2025-10-09 12:17:53.000000', '2025-10-09 12:17:53.000000', '250788317222', 'Airtime', 'PUR-1760005073235', 'completed', NULL, NULL, NULL, 'test_session_9033'),
(500.00, 2, '2025-10-09 12:18:40.000000', '2025-10-09 12:18:40.000000', 4, '2025-10-09 12:18:40.000000', '2025-10-09 12:18:40.000000', '0783876662', 'Airtime', 'PUR-1760005120959', 'completed', NULL, NULL, NULL, NULL),
(2000.00, 5, '2025-10-09 12:33:35.000000', '2025-10-09 12:33:35.000000', 5, '2025-10-09 12:33:35.000000', '2025-10-09 12:33:35.000000', '250788317222', 'Airtime', 'PUR-1760006015575', 'completed', NULL, NULL, NULL, 'test_session_443'),
(2000.00, 5, '2025-10-09 23:13:12.000000', '2025-10-09 23:13:12.000000', 6, '2025-10-09 23:13:12.000000', '2025-10-09 23:13:12.000000', '250788317222', 'Airtime', 'PUR-1760044392553', 'completed', NULL, NULL, NULL, 'test_session_493'),
(100.00, 10, '2025-10-10 10:56:19.000000', '2025-10-10 10:56:19.000000', 7, '2025-10-10 10:56:19.000000', '2025-10-10 10:56:19.000000', '0783876662', 'Airtime', 'PUR-1760086579520', 'completed', NULL, NULL, NULL, NULL),
(2000.00, 5, '2025-10-13 12:25:27.000000', '2025-10-13 12:25:27.000000', 8, '2025-10-13 12:25:27.000000', '2025-10-13 12:25:27.000000', '250788555555', 'Airtime', 'PUR-1760351127188', 'completed', NULL, NULL, NULL, 'test_purchase_1760351099'),
(1000.00, 92, '2025-10-13 12:27:44.000000', '2025-10-13 12:27:44.000000', 9, '2025-10-13 12:27:44.000000', '2025-10-13 12:27:44.000000', '0783876662', 'Airtime', 'PUR-1760351264450', 'completed', NULL, NULL, NULL, NULL),
(200.00, 1, '2025-10-13 12:29:18.000000', '2025-10-13 12:29:18.000000', 11, '2025-10-13 12:29:18.000000', '2025-10-13 12:29:18.000000', '250788888888', 'MoMo', 'PUR-1760351358614', 'completed', NULL, NULL, NULL, 'test_real_bundle_1760351339'),
(2000.00, 5, '2025-10-13 12:30:46.000000', '2025-10-13 12:30:46.000000', 12, '2025-10-13 12:30:46.000000', '2025-10-13 12:30:46.000000', '250788111111', 'Airtime', 'PUR-1760351446293', 'completed', NULL, NULL, NULL, 'test_gwamon_1760351428'),
(100.00, 20, '2025-10-13 12:31:38.000000', '2025-10-13 12:31:38.000000', 13, '2025-10-13 12:31:38.000000', '2025-10-13 12:31:38.000000', '250788333333', 'MoMo', 'PUR-1760351498585', 'completed', NULL, NULL, NULL, 'test_other_1760351482'),
(0.00, 102, '2025-10-13 12:34:27.000000', '2025-10-13 12:34:27.000000', 16, '2025-10-13 12:34:27.000000', '2025-10-13 12:34:27.000000', '250788999999', 'MoMo', 'PUR-1760351667752', 'completed', NULL, NULL, NULL, 'test_placeholder_fixed_1760351644'),
(0.00, 103, '2025-10-13 12:35:37.000000', '2025-10-13 12:35:37.000000', 17, '2025-10-13 12:35:37.000000', '2025-10-13 12:35:37.000000', '250788317212', 'MoMo', 'PUR-1760351737743', 'completed', NULL, NULL, NULL, 'test_session_74'),
(1000.00, 103, '2025-10-13 12:38:40.000000', '2025-10-13 12:38:40.000000', 18, '2025-10-13 12:38:40.000000', '2025-10-13 12:38:40.000000', '250788444444', 'MoMo', 'PUR-1760351920367', 'completed', NULL, NULL, NULL, 'test_amount_fix_1760351890'),
(100.00, 100, '2025-10-13 12:40:26.000000', '2025-10-13 12:40:26.000000', 19, '2025-10-13 12:40:26.000000', '2025-10-13 12:40:26.000000', '250788100', 'MoMo', 'PUR-1760352026378', 'completed', NULL, NULL, NULL, 'test_bundle_100_1760352026'),
(200.00, 101, '2025-10-13 12:40:28.000000', '2025-10-13 12:40:28.000000', 20, '2025-10-13 12:40:28.000000', '2025-10-13 12:40:28.000000', '250788101', 'MoMo', 'PUR-1760352028704', 'completed', NULL, NULL, NULL, 'test_bundle_101_1760352028'),
(500.00, 102, '2025-10-13 12:40:32.000000', '2025-10-13 12:40:32.000000', 21, '2025-10-13 12:40:32.000000', '2025-10-13 12:40:32.000000', '250788102', 'MoMo', 'PUR-1760352032059', 'completed', NULL, NULL, NULL, 'test_bundle_102_1760352031'),
(1000.00, 103, '2025-10-13 12:40:34.000000', '2025-10-13 12:40:34.000000', 22, '2025-10-13 12:40:34.000000', '2025-10-13 12:40:34.000000', '250788103', 'MoMo', 'PUR-1760352034730', 'completed', NULL, NULL, NULL, 'test_bundle_103_1760352034'),
(500.00, 105, '2025-10-13 12:40:37.000000', '2025-10-13 12:40:37.000000', 23, '2025-10-13 12:40:37.000000', '2025-10-13 12:40:37.000000', '250788111', 'MoMo', 'PUR-1760352037024', 'completed', NULL, NULL, NULL, 'test_bundle_111_1760352036'),
(1000.00, 106, '2025-10-13 12:40:39.000000', '2025-10-13 12:40:39.000000', 24, '2025-10-13 12:40:39.000000', '2025-10-13 12:40:39.000000', '250788112', 'MoMo', 'PUR-1760352039323', 'completed', NULL, NULL, NULL, 'test_bundle_112_1760352039'),
(1000.00, 107, '2025-10-13 12:42:44.000000', '2025-10-13 12:42:44.000000', 25, '2025-10-13 12:42:44.000000', '2025-10-13 12:42:44.000000', '250788104104', 'MoMo', 'PUR-1760352164585', 'completed', NULL, NULL, NULL, 'test_monthly_104_1760352164'),
(1000.00, 103, '2025-10-13 12:43:55.000000', '2025-10-13 12:43:55.000000', 26, '2025-10-13 12:43:55.000000', '2025-10-13 12:43:55.000000', '250788317212', 'MoMo', 'PUR-1760352235755', 'completed', NULL, NULL, NULL, 'test_session_904'),
(100.00, 100, '2025-10-13 12:52:38.000000', '2025-10-13 12:52:38.000000', 27, '2025-10-13 12:52:38.000000', '2025-10-13 12:52:38.000000', '250788317212', 'MoMo', 'PUR-1760352758089', 'completed', NULL, NULL, NULL, 'test_session_9999'),
(2000.00, 5, '2025-10-13 18:25:26.000000', '2025-10-13 18:25:26.000000', 28, '2025-10-13 18:25:26.000000', '2025-10-13 18:25:26.000000', '250788317212', 'MoMo', 'PUR-1760372726669', 'completed', NULL, NULL, NULL, 'test_session_989'),
(2000.00, 5, '2025-10-28 11:38:51.000000', '2025-10-28 11:38:51.000000', 29, '2025-10-28 11:38:51.000000', '2025-10-28 11:38:51.000000', '250788317212', 'MoMo', 'PUR-1761644331396', 'completed', NULL, NULL, NULL, 'test_session_119'),
(1000.00, 103, '2025-10-28 12:04:19.000000', '2025-10-28 12:04:19.000000', 30, '2025-10-28 12:04:19.000000', '2025-10-28 12:04:19.000000', '250788317212', 'MoMo', 'PUR-1761645859220', 'completed', NULL, NULL, NULL, 'test_session_666'),
(200.00, 1, '2025-11-01 12:39:46.000000', '2025-11-01 12:39:46.000000', 31, '2025-11-01 12:39:46.000000', '2025-11-01 12:39:46.000000', '0783876662', 'Airtime', 'PUR-1761993586967', 'completed', NULL, NULL, NULL, NULL),
(2000.00, 5, '2025-11-01 12:40:59.000000', '2025-11-01 12:40:59.000000', 32, '2025-11-01 12:40:59.000000', '2025-11-01 12:40:59.000000', '250788317212', 'MoMo', 'PUR-1761993659582', 'completed', NULL, NULL, NULL, '5030'),
(1000.00, 103, '2025-11-03 10:37:40.000000', '2025-11-03 10:37:40.000000', 33, '2025-11-03 10:37:40.000000', '2025-11-03 10:37:40.000000', '250788317212', 'MoMo', 'PUR-1762159060616', 'completed', NULL, NULL, NULL, '5222'),
(1000.00, 103, '2025-11-03 10:51:25.000000', '2025-11-03 10:51:25.000000', 34, '2025-11-03 10:51:25.000000', '2025-11-03 10:51:25.000000', '250788317212', 'MoMo', 'PUR-1762159885572', 'completed', NULL, NULL, NULL, '52e22'),
(1000.00, 103, '2025-11-03 10:51:45.000000', '2025-11-03 10:51:45.000000', 35, '2025-11-03 10:51:45.000000', '2025-11-03 10:51:45.000000', '250788317212', 'MoMo', 'PUR-1762159905597', 'completed', NULL, NULL, NULL, '521112'),
(1000.00, 103, '2025-11-03 11:08:46.000000', '2025-11-03 11:08:46.000000', 36, '2025-11-03 11:08:46.000000', '2025-11-03 11:08:46.000000', '250788317212', 'MoMo', 'PUR-1762160926790', 'completed', NULL, NULL, NULL, '5912'),
(1000.00, 103, '2025-11-05 12:19:48.000000', '2025-11-05 12:19:48.000000', 37, '2025-11-05 12:19:48.000000', '2025-11-05 12:19:48.000000', '250788317212', 'MoMo', 'PUR-1762337988074', 'completed', NULL, NULL, NULL, '91112ff9'),
(1000.00, 103, '2025-11-05 12:41:08.000000', '2025-11-05 12:41:08.000000', 38, '2025-11-05 12:41:08.000000', '2025-11-05 12:41:08.000000', '250788317212', 'MoMo', 'PUR-1762339268573', 'completed', NULL, NULL, NULL, '9119239'),
(1000.00, 103, '2025-11-05 12:44:26.000000', '2025-11-05 12:44:26.000000', 39, '2025-11-05 12:44:26.000000', '2025-11-05 12:44:26.000000', '250788317212', 'MoMo', 'PUR-1762339466754', 'completed', NULL, NULL, NULL, '91f'),
(100.00, 20, '2025-11-05 12:47:38.000000', '2025-11-05 12:47:38.000000', 40, '2025-11-05 12:47:38.000000', '2025-11-05 12:47:38.000000', '250788317212', 'Airtime', 'PUR-1762339658594', 'completed', NULL, NULL, NULL, '9188f'),
(1000.00, 103, '2025-11-06 15:02:36.000000', '2025-11-06 15:02:36.000000', 41, '2025-11-06 15:02:36.000000', '2025-11-06 15:02:36.000000', '250788317212', 'MoMo', 'PUR-1762434156858', 'completed', NULL, NULL, NULL, '9177f'),
(1000.00, 103, '2025-11-06 15:58:09.000000', '2025-11-06 15:58:09.000000', 42, '2025-11-06 15:58:09.000000', '2025-11-06 15:58:09.000000', '250788317212', 'MoMo', 'PUR-1762437489110', 'completed', NULL, NULL, NULL, '993393'),
(1000.00, 103, '2025-11-10 14:42:20.000000', '2025-11-10 14:42:20.000000', 43, '2025-11-10 14:42:20.000000', '2025-11-10 14:42:20.000000', '250788317212', 'MoMo', 'PUR-1762778540471', 'completed', NULL, NULL, NULL, '9992303'),
(1000.00, 103, '2025-11-10 15:03:43.000000', '2025-11-10 15:03:43.000000', 44, '2025-11-10 15:03:43.000000', '2025-11-10 15:03:43.000000', '250788317212', 'MoMo', 'PUR-1762779823742', 'completed', NULL, NULL, NULL, '999777703');

-- --------------------------------------------------------

--
-- Table structure for table `session_logs`
--

CREATE TABLE `session_logs` (
  `success` bit(1) DEFAULT NULL,
  `bundle_id` bigint(20) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `action_type` varchar(50) NOT NULL,
  `purchase_id` varchar(50) DEFAULT NULL,
  `current_state` varchar(100) DEFAULT NULL,
  `error_message` text DEFAULT NULL,
  `metadata` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`metadata`)),
  `response_sent` text DEFAULT NULL,
  `session_id` varchar(255) NOT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `user_input` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `session_logs`
--

INSERT INTO `session_logs` (`success`, `bundle_id`, `created_at`, `id`, `phone_number`, `ip_address`, `action_type`, `purchase_id`, `current_state`, `error_message`, `metadata`, `response_sent`, `session_id`, `user_agent`, `user_input`) VALUES
(b'0', NULL, '2025-10-08 15:11:57.000000', 1, '250788123456', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_logging_001', NULL, ''),
(b'0', NULL, '2025-10-08 15:11:57.000000', 2, '250788123456', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_logging_001', NULL, '0'),
(b'0', NULL, '2025-10-08 15:12:43.000000', 3, '250788999888', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_log_002', NULL, ''),
(b'0', NULL, '2025-10-08 15:13:09.000000', 4, '250788111222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_complete_flow', NULL, ''),
(b'0', NULL, '2025-10-08 15:13:10.000000', 5, '250788111222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_complete_flow', NULL, '0'),
(b'0', NULL, '2025-10-08 15:14:30.000000', 6, '250788555666', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'final_test_001', NULL, ''),
(b'0', NULL, '2025-10-08 15:16:29.000000', 7, '250788777888', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'transactional_test', NULL, ''),
(b'0', NULL, '2025-10-08 15:18:11.000000', 8, '250788999000', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'full_trans_test', NULL, ''),
(b'0', NULL, '2025-10-08 15:18:46.000000', 9, '250788111222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'debug_test_123', NULL, ''),
(b'0', NULL, '2025-10-08 15:21:49.000000', 10, '250788123456', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'service_test_001', NULL, ''),
(b'0', NULL, '2025-10-08 15:23:13.000000', 11, '250788999000', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'complete_flow_test', NULL, ''),
(b'0', NULL, '2025-10-08 15:23:14.000000', 12, '250788999000', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'complete_flow_test', NULL, '0'),
(b'0', NULL, '2025-10-08 15:25:06.000000', 13, '250788000111', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'final_working_test', NULL, ''),
(b'0', NULL, '2025-10-08 15:26:48.000000', 14, '250788000222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'debug_autowire', NULL, ''),
(b'0', NULL, '2025-10-08 15:27:25.000000', 15, '250788333444', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'proof_of_logging', NULL, ''),
(b'0', NULL, '2025-10-08 15:27:44.000000', 16, '250788555666', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'live_test_final', NULL, ''),
(b'0', NULL, '2025-10-08 15:27:44.000000', 17, '250788555666', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'live_test_final', NULL, '0'),
(b'0', NULL, '2025-10-08 15:29:01.000000', 18, '233241234567', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_3230', NULL, ''),
(b'0', NULL, '2025-10-08 15:30:34.000000', 19, '233241234567', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Other Bundles\nChoose an option:\n\n1) 100Frw=100MB/24hrs\n2) 500Frw=500MB/7Days\n3) 1000Frw=800MB/30Days\n4) DesaDe\n0) Go back', 'test_session_3230', NULL, '3'),
(b'1', 21, '2025-10-08 15:31:04.000000', 20, '233241234567', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 500Frw=500MB/7Days via Airtime', 'test_session_3230', NULL, '1'),
(b'0', NULL, '2025-10-08 15:36:26.000000', 21, '233241234567', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', 'test_session_3230', NULL, ''),
(b'0', NULL, '2025-10-08 15:51:42.000000', 22, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_890', NULL, ''),
(b'0', NULL, '2025-10-09 12:06:54.000000', 23, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_898', NULL, ''),
(b'0', NULL, '2025-10-09 12:06:58.000000', 24, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Other Bundles\nChoose an option:\n\n1) 100Frw=100MB/24hrs\n2) 500Frw=500MB/7Days\n3) 1000Frw=800MB/30Days\n4) DesaDe\n0) Go back', 'test_session_898', NULL, '3'),
(b'0', NULL, '2025-10-09 12:17:04.000000', 25, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_9099', NULL, ''),
(b'0', NULL, '2025-10-09 12:17:13.000000', 26, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_9099', NULL, '2'),
(b'0', NULL, '2025-10-09 12:17:40.000000', 27, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_9033', NULL, ''),
(b'0', NULL, '2025-10-09 12:17:44.000000', 28, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_session_9033', NULL, '0'),
(b'0', 5, '2025-10-09 12:17:49.000000', 29, '250788317222', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: 2000FRW=10GB+1000Mins+50SMS', 'test_session_9033', NULL, '2'),
(b'1', 5, '2025-10-09 12:17:53.000000', 30, '250788317222', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 2000FRW=10GB+1000Mins+50SMS via Airtime', 'test_session_9033', NULL, '1'),
(b'0', NULL, '2025-10-09 12:19:19.000000', 31, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_9053', NULL, ''),
(b'0', NULL, '2025-10-09 12:20:14.000000', 32, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_9053', NULL, ''),
(b'0', NULL, '2025-10-09 12:20:41.000000', 33, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_9053', NULL, '2'),
(b'0', NULL, '2025-10-09 12:26:06.000000', 34, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_8053', NULL, ''),
(b'0', NULL, '2025-10-09 12:26:11.000000', 35, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_8053', NULL, '2'),
(b'0', NULL, '2025-10-09 12:33:13.000000', 36, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_443', NULL, ''),
(b'0', NULL, '2025-10-09 12:33:22.000000', 37, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_session_443', NULL, '0'),
(b'0', 5, '2025-10-09 12:33:30.000000', 38, '250788317222', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: 2000FRW=10GB+1000Mins+50SMS', 'test_session_443', NULL, '2'),
(b'1', 5, '2025-10-09 12:33:35.000000', 39, '250788317222', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 2000FRW=10GB+1000Mins+50SMS via Airtime', 'test_session_443', NULL, '1'),
(b'0', NULL, '2025-10-09 16:19:43.000000', 40, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_463', NULL, ''),
(b'0', NULL, '2025-10-09 23:12:47.000000', 41, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_493', NULL, ''),
(b'0', NULL, '2025-10-09 23:13:01.000000', 42, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_session_493', NULL, '0'),
(b'0', 5, '2025-10-09 23:13:08.000000', 43, '250788317222', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: 2000FRW=10GB+1000Mins+50SMS', 'test_session_493', NULL, '2'),
(b'1', 5, '2025-10-09 23:13:12.000000', 44, '250788317222', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 2000FRW=10GB+1000Mins+50SMS via Airtime', 'test_session_493', NULL, '1'),
(b'0', NULL, '2025-10-09 23:13:21.000000', 45, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_993', NULL, ''),
(b'0', NULL, '2025-10-09 23:13:49.000000', 46, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_session_993', NULL, '0'),
(b'0', 5, '2025-10-09 23:14:03.000000', 47, '250788317222', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: 2000FRW=10GB+1000Mins+50SMS', 'test_session_993', NULL, '2'),
(b'0', NULL, '2025-10-10 10:56:30.000000', 48, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_9933', NULL, ''),
(b'0', NULL, '2025-10-10 10:56:36.000000', 49, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_session_9933', NULL, '0'),
(b'0', 4, '2025-10-10 10:56:39.000000', 50, '250788317222', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: Gwamon Weekend', 'test_session_9933', NULL, '1'),
(b'0', NULL, '2025-10-10 11:02:00.000000', 51, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_921', NULL, ''),
(b'0', NULL, '2025-10-10 11:02:06.000000', 52, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_921', NULL, '2'),
(b'1', 103, '2025-10-10 11:02:13.000000', 53, '250788317222', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', 'test_session_921', NULL, '2'),
(b'0', NULL, '2025-10-13 12:20:43.000000', 54, '250788111111', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_xampp_1760350843', NULL, ''),
(b'0', NULL, '2025-10-13 12:23:36.000000', 55, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_39', NULL, ''),
(b'0', NULL, '2025-10-13 12:23:40.000000', 56, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_39', NULL, '2'),
(b'1', 103, '2025-10-13 12:23:41.000000', 57, '250788317222', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', 'test_session_39', NULL, '2'),
(b'0', NULL, '2025-10-13 12:24:59.000000', 58, '250788555555', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_purchase_1760351099', NULL, ''),
(b'0', NULL, '2025-10-13 12:25:12.000000', 59, '250788555555', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_purchase_1760351099', NULL, '0'),
(b'0', 5, '2025-10-13 12:25:20.000000', 60, '250788555555', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: 2000FRW=10GB+1000Mins+50SMS', 'test_purchase_1760351099', NULL, '2'),
(b'1', 5, '2025-10-13 12:25:27.000000', 61, '250788555555', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 2000FRW=10GB+1000Mins+50SMS via Airtime', 'test_purchase_1760351099', NULL, '1'),
(b'0', NULL, '2025-10-13 12:26:29.000000', 62, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_34', NULL, ''),
(b'0', NULL, '2025-10-13 12:26:31.000000', 63, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_34', NULL, '2'),
(b'1', 103, '2025-10-13 12:26:35.000000', 64, '250788317222', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', 'test_session_34', NULL, '2'),
(b'0', NULL, '2025-10-13 12:28:14.000000', 65, '250788777777', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_placeholder_1760351294', NULL, ''),
(b'0', NULL, '2025-10-13 12:28:29.000000', 66, '250788777777', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_placeholder_1760351294', NULL, '2'),
(b'0', NULL, '2025-10-13 12:28:59.000000', 67, '250788888888', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_real_bundle_1760351339', NULL, ''),
(b'0', NULL, '2025-10-13 12:29:10.000000', 68, '250788888888', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Triple Data Promo\nChoose an option:\n\n1) 200Frw=600MB+300MB(Night)/24hrs\n2) 500Frw=2GB+1GB (Night)/24hrs\n3) 1000Frw=2GB+1GB (Night)/7dys\n0) Go back', 'test_real_bundle_1760351339', NULL, '99'),
(b'1', 1, '2025-10-13 12:29:18.000000', 69, '250788888888', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 200Frw=600MB+300MB(Night)/24hrs via MoMo', 'test_real_bundle_1760351339', NULL, '2'),
(b'0', NULL, '2025-10-13 12:30:28.000000', 70, '250788111111', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_gwamon_1760351428', NULL, ''),
(b'0', NULL, '2025-10-13 12:30:37.000000', 71, '250788111111', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_gwamon_1760351428', NULL, '0'),
(b'0', 5, '2025-10-13 12:30:44.000000', 72, '250788111111', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: 2000FRW=10GB+1000Mins+50SMS', 'test_gwamon_1760351428', NULL, '2'),
(b'1', 5, '2025-10-13 12:30:46.000000', 73, '250788111111', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 2000FRW=10GB+1000Mins+50SMS via Airtime', 'test_gwamon_1760351428', NULL, '1'),
(b'0', NULL, '2025-10-13 12:30:54.000000', 74, '250788222222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_placeholder_1760351454', NULL, ''),
(b'0', NULL, '2025-10-13 12:31:03.000000', 75, '250788222222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_placeholder_1760351454', NULL, '2'),
(b'0', NULL, '2025-10-13 12:31:22.000000', 76, '250788333333', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_other_1760351482', NULL, ''),
(b'0', NULL, '2025-10-13 12:31:30.000000', 77, '250788333333', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Other Bundles\nChoose an option:\n\n1) 100Frw=100MB/24hrs\n2) 500Frw=500MB/7Days\n3) 1000Frw=800MB/30Days\n4) DesaDe\n0) Go back', 'test_other_1760351482', NULL, '3'),
(b'1', 20, '2025-10-13 12:31:38.000000', 78, '250788333333', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 100Frw=100MB/24hrs via MoMo', 'test_other_1760351482', NULL, '2'),
(b'0', NULL, '2025-10-13 12:32:43.000000', 79, '250788317222', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_54', NULL, ''),
(b'0', NULL, '2025-10-13 12:32:45.000000', 80, '250788317222', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_54', NULL, '2'),
(b'0', NULL, '2025-10-13 12:33:00.000000', 81, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', 'test_session_34', NULL, ''),
(b'0', NULL, '2025-10-13 12:33:05.000000', 82, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_44', NULL, ''),
(b'0', NULL, '2025-10-13 12:33:07.000000', 83, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_44', NULL, '2'),
(b'0', NULL, '2025-10-13 12:34:04.000000', 84, '250788999999', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_placeholder_fixed_1760351644', NULL, ''),
(b'0', NULL, '2025-10-13 12:34:14.000000', 85, '250788999999', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_placeholder_fixed_1760351644', NULL, '2'),
(b'1', 102, '2025-10-13 12:34:27.000000', 86, '250788999999', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 102 via MoMo', 'test_placeholder_fixed_1760351644', NULL, '2'),
(b'0', NULL, '2025-10-13 12:35:31.000000', 87, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_74', NULL, ''),
(b'0', NULL, '2025-10-13 12:35:35.000000', 88, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_74', NULL, '2'),
(b'1', 103, '2025-10-13 12:35:37.000000', 89, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', 'test_session_74', NULL, '2'),
(b'0', NULL, '2025-10-13 12:38:10.000000', 90, '250788444444', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_amount_fix_1760351890', NULL, ''),
(b'0', NULL, '2025-10-13 12:38:20.000000', 91, '250788444444', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_amount_fix_1760351890', NULL, '2'),
(b'1', 103, '2025-10-13 12:38:40.000000', 92, '250788444444', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', 'test_amount_fix_1760351890', NULL, '2'),
(b'0', NULL, '2025-10-13 12:39:44.000000', 93, '250788111111', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_daily_1760351984', NULL, ''),
(b'0', NULL, '2025-10-13 12:39:56.000000', 94, '250788100100', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_bundle_100_1760351996', NULL, ''),
(b'0', NULL, '2025-10-13 12:40:26.000000', 95, '250788100', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_bundle_100_1760352026', NULL, ''),
(b'0', NULL, '2025-10-13 12:40:26.000000', 96, '250788100', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_bundle_100_1760352026', NULL, '2'),
(b'1', 100, '2025-10-13 12:40:26.000000', 97, '250788100', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 100 via MoMo', 'test_bundle_100_1760352026', NULL, '2'),
(b'0', NULL, '2025-10-13 12:40:28.000000', 98, '250788101', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_bundle_101_1760352028', NULL, ''),
(b'0', NULL, '2025-10-13 12:40:28.000000', 99, '250788101', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_bundle_101_1760352028', NULL, '2'),
(b'1', 101, '2025-10-13 12:40:28.000000', 100, '250788101', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 101 via MoMo', 'test_bundle_101_1760352028', NULL, '2'),
(b'0', NULL, '2025-10-13 12:40:31.000000', 101, '250788102', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_bundle_102_1760352031', NULL, ''),
(b'0', NULL, '2025-10-13 12:40:31.000000', 102, '250788102', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_bundle_102_1760352031', NULL, '2'),
(b'1', 102, '2025-10-13 12:40:32.000000', 103, '250788102', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 102 via MoMo', 'test_bundle_102_1760352031', NULL, '2'),
(b'0', NULL, '2025-10-13 12:40:34.000000', 104, '250788103', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_bundle_103_1760352034', NULL, ''),
(b'0', NULL, '2025-10-13 12:40:34.000000', 105, '250788103', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_bundle_103_1760352034', NULL, '2'),
(b'1', 103, '2025-10-13 12:40:34.000000', 106, '250788103', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', 'test_bundle_103_1760352034', NULL, '2'),
(b'0', NULL, '2025-10-13 12:40:36.000000', 107, '250788111', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_bundle_111_1760352036', NULL, ''),
(b'0', NULL, '2025-10-13 12:40:36.000000', 108, '250788111', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_bundle_111_1760352036', NULL, '2'),
(b'1', 105, '2025-10-13 12:40:37.000000', 109, '250788111', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 105 via MoMo', 'test_bundle_111_1760352036', NULL, '2'),
(b'0', NULL, '2025-10-13 12:40:39.000000', 110, '250788112', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_bundle_112_1760352039', NULL, ''),
(b'0', NULL, '2025-10-13 12:40:39.000000', 111, '250788112', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_bundle_112_1760352039', NULL, '2'),
(b'1', 106, '2025-10-13 12:40:39.000000', 112, '250788112', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 106 via MoMo', 'test_bundle_112_1760352039', NULL, '2'),
(b'0', NULL, '2025-10-13 12:40:46.000000', 113, '250788111111', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_social_1760352046', NULL, ''),
(b'0', NULL, '2025-10-13 12:40:50.000000', 114, '250788111111', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_social_1760352046', NULL, '2'),
(b'0', NULL, '2025-10-13 12:41:37.000000', 115, '250788111111', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_whatsapp_111_1760352097', NULL, ''),
(b'0', NULL, '2025-10-13 12:41:37.000000', 116, '250788111111', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_whatsapp_111_1760352097', NULL, '2'),
(b'0', NULL, '2025-10-13 12:42:07.000000', 117, '250788111111', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_whatsapp_final_1760352127', NULL, ''),
(b'0', NULL, '2025-10-13 12:42:07.000000', 118, '250788111111', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_whatsapp_final_1760352127', NULL, '2'),
(b'0', NULL, '2025-10-13 12:42:12.000000', 119, '250788111111', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'debug_whatsapp_1760352132', NULL, ''),
(b'0', NULL, '2025-10-13 12:42:44.000000', 120, '250788104104', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_monthly_104_1760352164', NULL, ''),
(b'0', NULL, '2025-10-13 12:42:44.000000', 121, '250788104104', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_monthly_104_1760352164', NULL, '2'),
(b'1', 107, '2025-10-13 12:42:44.000000', 122, '250788104104', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 107 via MoMo', 'test_monthly_104_1760352164', NULL, '2'),
(b'0', NULL, '2025-10-13 12:43:41.000000', 123, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', 'test_session_74', NULL, ''),
(b'0', NULL, '2025-10-13 12:43:46.000000', 124, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', 'test_session_34', NULL, ''),
(b'0', NULL, '2025-10-13 12:43:50.000000', 125, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_904', NULL, ''),
(b'0', NULL, '2025-10-13 12:43:53.000000', 126, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_904', NULL, '2'),
(b'1', 103, '2025-10-13 12:43:55.000000', 127, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', 'test_session_904', NULL, '2'),
(b'0', NULL, '2025-10-13 12:47:35.000000', 128, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_9999', NULL, ''),
(b'0', NULL, '2025-10-13 12:52:25.000000', 129, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_9999', NULL, '2'),
(b'1', 100, '2025-10-13 12:52:38.000000', 130, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 100 via MoMo', 'test_session_9999', NULL, '2'),
(b'0', NULL, '2025-10-13 16:37:32.000000', 131, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_9889', NULL, ''),
(b'0', NULL, '2025-10-13 18:24:11.000000', 132, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_989', NULL, ''),
(b'0', NULL, '2025-10-13 18:24:52.000000', 133, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_session_989', NULL, '0'),
(b'0', 5, '2025-10-13 18:25:04.000000', 134, '250788317212', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: 2000FRW=10GB+1000Mins+50SMS', 'test_session_989', NULL, '2'),
(b'1', 5, '2025-10-13 18:25:26.000000', 135, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 2000FRW=10GB+1000Mins+50SMS via MoMo', 'test_session_989', NULL, '2'),
(b'0', NULL, '2025-10-13 18:25:55.000000', 136, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_119', NULL, ''),
(b'0', NULL, '2025-10-28 11:38:38.000000', 137, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_119', NULL, ''),
(b'0', NULL, '2025-10-28 11:38:44.000000', 138, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', 'test_session_119', NULL, '0'),
(b'0', 5, '2025-10-28 11:38:50.000000', 139, '250788317212', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: 2000FRW=10GB+1000Mins+50SMS', 'test_session_119', NULL, '2'),
(b'1', 5, '2025-10-28 11:38:51.000000', 140, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 2000FRW=10GB+1000Mins+50SMS via MoMo', 'test_session_119', NULL, '2'),
(b'0', NULL, '2025-10-28 12:04:11.000000', 141, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test_session_666', NULL, ''),
(b'0', NULL, '2025-10-28 12:04:15.000000', 142, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', 'test_session_666', NULL, '2'),
(b'1', 103, '2025-10-28 12:04:19.000000', 143, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', 'test_session_666', NULL, '2'),
(b'0', NULL, '2025-10-28 12:15:46.000000', 144, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9991', NULL, ''),
(b'0', NULL, '2025-10-28 12:27:07.000000', 145, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9981', NULL, ''),
(b'0', NULL, '2025-10-28 12:29:05.000000', 146, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', '9981', NULL, '2'),
(b'0', NULL, '2025-10-28 12:29:26.000000', 147, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9981', NULL, ''),
(b'0', NULL, '2025-10-28 12:29:34.000000', 148, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9481', NULL, ''),
(b'0', NULL, '2025-10-28 12:30:19.000000', 149, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', '9481', NULL, '2'),
(b'0', NULL, '2025-10-29 18:23:51.000000', 150, '250788123456', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test123', NULL, ''),
(b'0', NULL, '2025-10-29 18:23:56.000000', 151, '250788123456', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*345#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test456', NULL, ''),
(b'0', NULL, '2025-10-29 18:23:59.000000', 152, '250788123456', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*140#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', 'test789', NULL, ''),
(b'0', NULL, '2025-10-29 18:24:14.000000', 153, '250788123456', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Voice\nChoose an option:\n\n1) 100Frw=50Mins+20SMS/24hrs\n2) 200Frw=250Mins+20SMS/24hrs\n3) 500Frw=800Mins+20SMS/7Days\n4) 1000Frw=(120 Mins+1GB) per day /7days\nn) Next\n0) Go back', 'xmltest123', NULL, '1'),
(b'0', NULL, '2025-10-29 19:05:31.000000', 154, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9481', NULL, ''),
(b'0', NULL, '2025-10-29 19:05:34.000000', 155, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9481', NULL, ''),
(b'0', NULL, '2025-10-29 19:05:35.000000', 156, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9481', NULL, ''),
(b'0', NULL, '2025-10-29 19:05:36.000000', 157, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9481', NULL, ''),
(b'0', NULL, '2025-10-29 19:05:37.000000', 158, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9481', NULL, ''),
(b'0', NULL, '2025-10-29 19:05:38.000000', 159, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9481', NULL, ''),
(b'0', NULL, '2025-10-29 19:05:42.000000', 160, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '948', NULL, ''),
(b'0', NULL, '2025-10-29 19:05:44.000000', 161, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '948', NULL, ''),
(b'0', NULL, '2025-10-29 20:07:02.000000', 162, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9r48', NULL, '*154#');
INSERT INTO `session_logs` (`success`, `bundle_id`, `created_at`, `id`, `phone_number`, `ip_address`, `action_type`, `purchase_id`, `current_state`, `error_message`, `metadata`, `response_sent`, `session_id`, `user_agent`, `user_input`) VALUES
(b'0', NULL, '2025-10-29 20:07:03.000000', 163, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9r48', NULL, '*154#'),
(b'0', NULL, '2025-10-29 20:07:04.000000', 164, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9r48', NULL, '*154#'),
(b'0', NULL, '2025-10-29 20:07:05.000000', 165, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9r48', NULL, '*154#'),
(b'0', NULL, '2025-10-29 20:07:18.000000', 166, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9000', NULL, '*345#'),
(b'0', NULL, '2025-10-29 20:07:22.000000', 167, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9000', NULL, '*345#'),
(b'0', NULL, '2025-10-29 20:07:24.000000', 168, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9000', NULL, '*345#'),
(b'0', NULL, '2025-10-29 20:08:04.000000', 169, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9030', NULL, '*154#'),
(b'0', NULL, '2025-10-29 20:08:10.000000', 170, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '9030', NULL, '2'),
(b'0', NULL, '2025-11-01 12:40:12.000000', 171, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5000', NULL, ''),
(b'0', NULL, '2025-11-01 12:40:24.000000', 172, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '5000', NULL, '0'),
(b'0', NULL, '2025-11-01 12:40:40.000000', 173, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5030', NULL, ''),
(b'0', NULL, '2025-11-01 12:40:51.000000', 174, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON Gwamon\' Bundles\nValid till Sunday 23:59\n\n1) Gwamon Weekend\n2) 2000FRW=10GB+1000Mins+50SMS\n3) 1000FRW=5GB+500Mins+20SMS\n0) Go back', '5030', NULL, '0'),
(b'0', 5, '2025-11-01 12:40:57.000000', 175, '250788317212', NULL, 'BUNDLE_SELECTED', NULL, 'Gwamon Menu', NULL, NULL, 'Selected: 2000FRW=10GB+1000Mins+50SMS', '5030', NULL, '2'),
(b'1', 5, '2025-11-01 12:40:59.000000', 176, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 2000FRW=10GB+1000Mins+50SMS via MoMo', '5030', NULL, '2'),
(b'0', NULL, '2025-11-03 10:20:03.000000', 177, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5050', NULL, ''),
(b'0', NULL, '2025-11-03 10:20:06.000000', 178, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '5050', NULL, ''),
(b'0', NULL, '2025-11-03 10:20:07.000000', 179, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '5050', NULL, ''),
(b'0', NULL, '2025-11-03 10:20:58.000000', 180, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5020', NULL, ''),
(b'0', NULL, '2025-11-03 10:21:05.000000', 181, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '5020', NULL, '2'),
(b'0', NULL, '2025-11-03 10:21:07.000000', 182, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'END Sorry, an error occurred. Please try again later.', '5020', NULL, '2'),
(b'0', NULL, '2025-11-03 10:23:45.000000', 183, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:38.000000', 184, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:39.000000', 185, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:39.000000', 186, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:40.000000', 187, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:40.000000', 188, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:40.000000', 189, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:41.000000', 190, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:43.000000', 191, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:43.000000', 192, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:46.000000', 193, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:47.000000', 194, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, ''),
(b'0', NULL, '2025-11-03 10:31:52.000000', 195, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, '1'),
(b'0', NULL, '2025-11-03 10:31:53.000000', 196, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, '1'),
(b'0', NULL, '2025-11-03 10:32:07.000000', 197, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, '1'),
(b'0', NULL, '2025-11-03 10:32:14.000000', 198, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5220', NULL, '3'),
(b'0', NULL, '2025-11-03 10:37:28.000000', 199, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5222', NULL, '1'),
(b'0', NULL, '2025-11-03 10:37:37.000000', 200, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', '5222', NULL, '2'),
(b'1', 103, '2025-11-03 10:37:40.000000', 201, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '5222', NULL, '2'),
(b'0', NULL, '2025-11-03 10:51:02.000000', 202, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5222', NULL, '154'),
(b'0', NULL, '2025-11-03 10:51:19.000000', 203, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', '52e22', NULL, '2'),
(b'1', 103, '2025-11-03 10:51:25.000000', 204, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '52e22', NULL, '2'),
(b'0', NULL, '2025-11-03 10:51:35.000000', 205, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '521112', NULL, ''),
(b'0', NULL, '2025-11-03 10:51:43.000000', 206, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', '521112', NULL, '2'),
(b'1', 103, '2025-11-03 10:51:45.000000', 207, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '521112', NULL, '2'),
(b'0', NULL, '2025-11-03 11:04:01.000000', 208, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '53112', NULL, '1'),
(b'0', NULL, '2025-11-03 11:04:03.000000', 209, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '53112', NULL, '1'),
(b'0', NULL, '2025-11-03 11:08:37.000000', 210, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5912', NULL, ''),
(b'0', NULL, '2025-11-03 11:08:44.000000', 211, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', '5912', NULL, '2'),
(b'1', 103, '2025-11-03 11:08:46.000000', 212, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '5912', NULL, '2'),
(b'0', NULL, '2025-11-05 12:08:00.000000', 213, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', '5212', NULL, '2'),
(b'0', NULL, '2025-11-05 12:08:04.000000', 214, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5212', NULL, ''),
(b'0', NULL, '2025-11-05 12:08:05.000000', 215, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5212', NULL, ''),
(b'0', NULL, '2025-11-05 12:08:05.000000', 216, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5212', NULL, ''),
(b'0', NULL, '2025-11-05 12:08:06.000000', 217, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5212', NULL, ''),
(b'0', NULL, '2025-11-05 12:08:17.000000', 218, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5212', NULL, '3'),
(b'0', NULL, '2025-11-05 12:08:21.000000', 219, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5212', NULL, '2'),
(b'0', NULL, '2025-11-05 12:08:39.000000', 220, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5550', NULL, ''),
(b'0', NULL, '2025-11-05 12:08:40.000000', 221, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5550', NULL, ''),
(b'0', NULL, '2025-11-05 12:08:41.000000', 222, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5550', NULL, ''),
(b'0', NULL, '2025-11-05 12:08:44.000000', 223, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '5550', NULL, '5'),
(b'0', NULL, '2025-11-05 12:12:22.000000', 224, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '94949', NULL, '1'),
(b'0', NULL, '2025-11-05 12:12:26.000000', 225, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '94949', NULL, '2'),
(b'0', NULL, '2025-11-05 12:15:19.000000', 226, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '911949', NULL, ''),
(b'0', NULL, '2025-11-05 12:15:24.000000', 227, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '911949', NULL, '3'),
(b'0', NULL, '2025-11-05 12:18:22.000000', 228, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91194ff9', NULL, ''),
(b'0', NULL, '2025-11-05 12:18:26.000000', 229, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91194ff9', NULL, '3'),
(b'0', NULL, '2025-11-05 12:19:31.000000', 230, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91194ff9', NULL, ''),
(b'0', NULL, '2025-11-05 12:19:33.000000', 231, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91194ff9', NULL, ''),
(b'0', NULL, '2025-11-05 12:19:41.000000', 232, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91112ff9', NULL, '2'),
(b'0', NULL, '2025-11-05 12:19:45.000000', 233, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', '91112ff9', NULL, '2'),
(b'1', 103, '2025-11-05 12:19:48.000000', 234, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '91112ff9', NULL, '2'),
(b'0', NULL, '2025-11-05 12:22:15.000000', 235, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91112ff9', NULL, '2'),
(b'0', NULL, '2025-11-05 12:24:35.000000', 236, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111239', NULL, ''),
(b'0', NULL, '2025-11-05 12:24:36.000000', 237, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111239', NULL, ''),
(b'0', NULL, '2025-11-05 12:24:37.000000', 238, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111239', NULL, ''),
(b'0', NULL, '2025-11-05 12:24:37.000000', 239, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111239', NULL, ''),
(b'0', NULL, '2025-11-05 12:24:39.000000', 240, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111239', NULL, ''),
(b'0', NULL, '2025-11-05 12:26:14.000000', 241, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '1'),
(b'0', NULL, '2025-11-05 12:26:17.000000', 242, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2'),
(b'0', NULL, '2025-11-05 12:35:00.000000', 243, '250788317212', NULL, 'SESSION_START', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:35:02.000000', 244, '250788317212', NULL, 'SESSION_START', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:37:44.000000', 245, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:37:45.000000', 246, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:37:50.000000', 247, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:37:54.000000', 248, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:37:54.000000', 249, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:37:57.000000', 250, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:38:11.000000', 251, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:38:12.000000', 252, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9111r239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:39:47.000000', 253, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Weekly (7Days)\nChoose an option:\n\n1. 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru\n2. 1000Frw=(120Mins+1GB) ku munsi /iminsi 7\n3) 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)\nn) Next\n0) Go back', '9119239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:39:48.000000', 254, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Weekly (7Days)\nChoose an option:\n\n1. 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru\n2. 1000Frw=(120Mins+1GB) ku munsi /iminsi 7\n3) 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)\nn) Next\n0) Go back', '9119239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:40:47.000000', 255, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Weekly (7Days)\nChoose an option:\n\n1. 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru\n2. 1000Frw=(120Mins+1GB) ku munsi /iminsi 7\n3) 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)\nn) Next\n0) Go back', '9119239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:40:48.000000', 256, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Weekly (7Days)\nChoose an option:\n\n1. 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru\n2. 1000Frw=(120Mins+1GB) ku munsi /iminsi 7\n3) 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)\nn) Next\n0) Go back', '9119239', NULL, '2*2'),
(b'0', NULL, '2025-11-05 12:40:54.000000', 257, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n1. Airtime\n2. MoMo\n0. Go back', '9119239', NULL, '2*2*2'),
(b'0', NULL, '2025-11-05 12:41:00.000000', 258, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Payment Options\nChoose payment method:\n\n1. Airtime\n2. MoMo\n0. Go back', '9119239', NULL, '2*2*2*2'),
(b'0', NULL, '2025-11-05 12:41:02.000000', 259, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Payment Options\nChoose payment method:\n\n1. Airtime\n2. MoMo\n0. Go back', '9119239', NULL, '2*2*2*2'),
(b'0', NULL, '2025-11-05 12:41:02.000000', 260, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Payment Options\nChoose payment method:\n\n1. Airtime\n2. MoMo\n0. Go back', '9119239', NULL, '2*2*2*2'),
(b'1', 103, '2025-11-05 12:41:08.000000', 261, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '9119239', NULL, '2'),
(b'0', NULL, '2025-11-05 12:44:10.000000', 262, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Internet\nChoose an option:\n\n1) Daily(24hrs)\n2) Weekly (7Days)\n3) Monthly(30Days)\n4) DesaDe\n5) Hourly\n6) Social Media Bundles(24hrs)\n0) Go back', '91f', NULL, '2'),
(b'0', NULL, '2025-11-05 12:44:19.000000', 263, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n1. Airtime\n2. MoMo\n0. Go back', '91f', NULL, '2*2*2'),
(b'0', NULL, '2025-11-05 12:44:20.000000', 264, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n1. Airtime\n2. MoMo\n0. Go back', '91f', NULL, '2*2*2'),
(b'1', 103, '2025-11-05 12:44:26.000000', 265, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '91f', NULL, '2'),
(b'0', NULL, '2025-11-05 12:44:33.000000', 266, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91f', NULL, '2'),
(b'0', NULL, '2025-11-05 12:44:34.000000', 267, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91f', NULL, '2'),
(b'0', NULL, '2025-11-05 12:44:37.000000', 268, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91f', NULL, ''),
(b'0', NULL, '2025-11-05 12:46:58.000000', 269, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91f', NULL, '2'),
(b'0', NULL, '2025-11-05 12:46:59.000000', 270, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '91f', NULL, '2'),
(b'0', NULL, '2025-11-05 12:47:26.000000', 271, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9188f', NULL, '3*1*1'),
(b'0', NULL, '2025-11-05 12:47:32.000000', 272, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Payment Options\nChoose payment method:\n\n1. Airtime\n2. MoMo\n0. Go back', '9188f', NULL, '3*1*1'),
(b'1', 20, '2025-11-05 12:47:38.000000', 273, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: 100Frw=100MB/24hrs via Airtime', '9188f', NULL, '1'),
(b'0', NULL, '2025-11-06 15:02:02.000000', 274, '250788317212', NULL, 'MENU_SELECTION', NULL, 'Main Menu', NULL, NULL, 'CON YOLO Voice\nChoose an option:\n\n1) 100Frw=50Mins+20SMS/24hrs\n2) 200Frw=250Mins+20SMS/24hrs\n3) 500Frw=800Mins+20SMS/7Days\n4) 1000Frw=(120 Mins+1GB) per day /7days\nn) Next\n0) Go back', '9177f', NULL, '1'),
(b'0', NULL, '2025-11-06 15:02:10.000000', 275, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Payment Options\nChoose payment method:\n\n1. Airtime\n2. MoMo\n0. Go back', '9177f', NULL, '1*2*@'),
(b'0', NULL, '2025-11-06 15:02:14.000000', 276, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Payment Options\nChoose payment method:\n\n1. Airtime\n2. MoMo\n0. Go back', '9177f', NULL, '1*2*2'),
(b'0', NULL, '2025-11-06 15:02:15.000000', 277, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Payment Options\nChoose payment method:\n\n1. Airtime\n2. MoMo\n0. Go back', '9177f', NULL, '1*2*2'),
(b'0', NULL, '2025-11-06 15:02:20.000000', 278, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Payment Options\nChoose payment method:\n\n1. Airtime\n2. MoMo\n0. Go back', '9177f', NULL, '1*2*2*2'),
(b'0', NULL, '2025-11-06 15:02:27.000000', 279, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Weekly (7Days)\nChoose an option:\n\n1. 500Frw= 1024MB+30SMS+ 30Mins(Night bonus) /Icyumweru\n2. 1000Frw=(120Mins+1GB) ku munsi /iminsi 7\n3) 1000Frw=30GB/Monthly(1GB/Day+100Mins/Day)\nn) Next\n0) Go back', '9177f', NULL, '2*2*'),
(b'0', NULL, '2025-11-06 15:02:32.000000', 280, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n1. Airtime\n2. MoMo\n0. Go back', '9177f', NULL, '2*2*2'),
(b'1', 103, '2025-11-06 15:02:36.000000', 281, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '9177f', NULL, '2'),
(b'0', NULL, '2025-11-06 15:58:04.000000', 282, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n1. Airtime\n2. MoMo\n0. Go back', '993393', NULL, '2*2*2'),
(b'1', 103, '2025-11-06 15:58:09.000000', 283, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '993393', NULL, '2'),
(b'0', NULL, '2025-11-10 14:29:03.000000', 284, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n1. Airtime\n2. MoMo\n0. Go back', '9992393', NULL, '2*2*2'),
(b'0', NULL, '2025-11-10 14:41:50.000000', 285, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9992393', NULL, '2*2*2'),
(b'0', NULL, '2025-11-10 14:41:51.000000', 286, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9992393', NULL, '2*2*2'),
(b'0', NULL, '2025-11-10 14:41:58.000000', 287, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '9992393', NULL, '2*2*2'),
(b'0', NULL, '2025-11-10 14:42:04.000000', 288, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n1. Airtime\n2. MoMo\n0. Go back', '9992303', NULL, '2*2*2'),
(b'1', 103, '2025-11-10 14:42:20.000000', 289, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '9992303', NULL, '2'),
(b'0', NULL, '2025-11-10 15:03:28.000000', 290, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '999777703', NULL, ''),
(b'0', NULL, '2025-11-10 15:03:39.000000', 291, '250788317212', NULL, 'LONG_NAVIGATION', NULL, 'Long Navigation', NULL, NULL, 'CON Pay 1000Frw=(120Mins+1GB)/day for 7days via:\n1. Airtime\n2. MoMo\n0. Go back', '999777703', NULL, '2*2*2'),
(b'1', 103, '2025-11-10 15:03:43.000000', 292, '250788317212', NULL, 'PURCHASE_COMPLETED', NULL, 'Payment Menu', NULL, NULL, 'Purchase successful: Bundle ID: 103 via MoMo', '999777703', NULL, '2'),
(b'0', NULL, '2025-11-10 15:04:08.000000', 293, '250788317212', NULL, 'SESSION_START', NULL, 'Main Menu', NULL, NULL, 'CON YOLO (*154#)\nChoose an option:\n\n99) Triple Data Promo\n0) Gwamon\'\n2) YOLO Voice\n3) YOLO Internet\n4) Other Bundles\n5) DesaDe\n6) Balance check\n7) FoLeva\n8) Ihereze\n9) YOLO Star\n10) Hindura Ururimi(Language)\nn) Next', '999799703', NULL, '2*2*2');

-- --------------------------------------------------------

--
-- Table structure for table `user_analytics`
--

CREATE TABLE `user_analytics` (
  `total_purchases` int(11) DEFAULT NULL,
  `total_sessions` int(11) DEFAULT NULL,
  `total_spent` decimal(12,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `favorite_category_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `last_purchase_at` datetime(6) DEFAULT NULL,
  `last_session_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `phone_number` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ussd_sessions`
--

CREATE TABLE `ussd_sessions` (
  `is_active` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `current_category_id` bigint(20) DEFAULT NULL,
  `expires_at` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `selected_bundle_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `phone_number` varchar(20) NOT NULL,
  `current_state` varchar(100) DEFAULT NULL,
  `last_input` varchar(255) DEFAULT NULL,
  `navigation_path` text DEFAULT NULL,
  `session_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`session_data`)),
  `session_id` varchar(255) NOT NULL,
  `short_code` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ussd_sessions`
--

INSERT INTO `ussd_sessions` (`is_active`, `created_at`, `current_category_id`, `expires_at`, `id`, `selected_bundle_id`, `updated_at`, `phone_number`, `current_state`, `last_input`, `navigation_path`, `session_data`, `session_id`, `short_code`) VALUES
(b'0', '2025-11-10 14:29:03.000000', NULL, '2025-11-10 14:39:03.000000', 239, 103, '2025-11-10 14:41:50.000000', '250788317212', 'payment_menu', 'yolo_internet_weekly', NULL, NULL, '9992393', '154'),
(b'0', '2025-11-10 14:42:04.000000', NULL, '2025-11-10 14:52:20.000000', 246, 103, '2025-11-10 14:42:20.000000', '250788317212', 'payment_menu', '2', NULL, NULL, '9992303', '154'),
(b'0', '2025-11-10 15:03:28.000000', NULL, '2025-11-10 15:13:43.000000', 247, 103, '2025-11-10 15:03:43.000000', '250788317212', 'payment_menu', '2', NULL, NULL, '999777703', '154'),
(b'1', '2025-11-10 15:04:08.000000', NULL, '2025-11-10 15:14:08.000000', 248, NULL, '2025-11-10 15:04:08.000000', '250788317212', 'main_menu', NULL, NULL, NULL, '999799703', '154');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bundles`
--
ALTER TABLE `bundles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKafvdtoojvtsr4t49j7hv4myiy` (`code`);

--
-- Indexes for table `menu_categories`
--
ALTER TABLE `menu_categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKgtijde7dqd6twv2tiwava87e7` (`code`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKcteyyqdn3oh193h5ronc7ewta` (`purchase_id`),
  ADD KEY `FKmn7vluiv6tqc9679m23dp105j` (`bundle_id`);

--
-- Indexes for table `session_logs`
--
ALTER TABLE `session_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_analytics`
--
ALTER TABLE `user_analytics`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKmgc8mllsfwba7f82ytf6kwlh7` (`phone_number`);

--
-- Indexes for table `ussd_sessions`
--
ALTER TABLE `ussd_sessions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK43d4em7fyoqvxqnw2jyskfitq` (`session_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bundles`
--
ALTER TABLE `bundles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=93;

--
-- AUTO_INCREMENT for table `menu_categories`
--
ALTER TABLE `menu_categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `session_logs`
--
ALTER TABLE `session_logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=294;

--
-- AUTO_INCREMENT for table `user_analytics`
--
ALTER TABLE `user_analytics`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ussd_sessions`
--
ALTER TABLE `ussd_sessions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=249;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
