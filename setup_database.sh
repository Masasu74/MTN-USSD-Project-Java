#!/bin/bash

echo "🚀 Setting up MTN YOLO USSD Database..."
echo ""

# Run the complete database setup
mysql -u root -P 3307 -h localhost mtn_ussd < COMPLETE_DATABASE_SETUP.sql

echo ""
echo "✅ Database setup complete!"
echo ""
echo "📊 Verifying data..."
mysql -u root -P 3307 -h localhost mtn_ussd -e "SELECT COUNT(*) as total_categories FROM menu_categories;"
mysql -u root -P 3307 -h localhost mtn_ussd -e "SELECT COUNT(*) as total_bundles FROM bundles;"

echo ""
echo "🎉 All done! Now restart your Spring Boot application."
echo ""
echo "Test with: curl -X GET http://localhost:8080/api/bundles"
