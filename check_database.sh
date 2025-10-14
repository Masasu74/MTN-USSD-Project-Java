#!/bin/bash

# USSD Database Checker
# Quick script to view database records without phpMyAdmin

echo "================================================"
echo "   MTN USSD DATABASE CHECKER"
echo "================================================"
echo ""

echo "📊 TOTAL RECORDS:"
echo "----------------"
mysql -uroot mtn_ussd -e "SELECT 
    (SELECT COUNT(*) FROM ussd_sessions) as sessions,
    (SELECT COUNT(*) FROM purchases) as purchases,
    (SELECT COUNT(*) FROM bundles) as bundles,
    (SELECT COUNT(*) FROM menu_categories) as categories,
    (SELECT COUNT(*) FROM session_logs) as logs;"
echo ""

echo "📱 RECENT SESSIONS (Last 10):"
echo "-----------------------------"
mysql -uroot mtn_ussd -e "SELECT id, session_id, phone_number, current_state, is_active, created_at FROM ussd_sessions ORDER BY created_at DESC LIMIT 10;"
echo ""

echo "💰 ALL PURCHASES:"
echo "-----------------"
mysql -uroot mtn_ussd -e "SELECT id, purchase_id, phone_number, bundle_id, payment_method, amount, status, purchased_at FROM purchases ORDER BY purchased_at DESC;"
echo ""

echo "✅ ACTIVE SESSIONS:"
echo "-------------------"
mysql -uroot mtn_ussd -e "SELECT id, session_id, phone_number, current_state, expires_at FROM ussd_sessions WHERE is_active=1;"
echo ""

echo "📅 TODAY'S SESSIONS:"
echo "--------------------"
mysql -uroot mtn_ussd -e "SELECT COUNT(*) as today_sessions FROM ussd_sessions WHERE DATE(created_at) = CURDATE();"
echo ""

echo "================================================"
echo "For more details, run specific queries:"
echo "  mysql -uroot mtn_ussd -e 'QUERY HERE;'"
echo "================================================"

