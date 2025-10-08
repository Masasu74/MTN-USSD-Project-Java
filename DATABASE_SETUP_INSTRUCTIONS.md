# MTN YOLO USSD Database Setup Instructions

## Quick Setup (Run These Commands)

### 1. Create Database (if not exists)
```bash
mysql -u root -P 3307 -h localhost -e "CREATE DATABASE IF NOT EXISTS mtn_ussd CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

### 2. Insert All Data
```bash
mysql -u root -P 3307 -h localhost mtn_ussd < INSERT_ALL_DATA.sql
```

### 3. Verify Data
```bash
mysql -u root -P 3307 -h localhost mtn_ussd -e "SELECT COUNT(*) as total_categories FROM menu_categories; SELECT COUNT(*) as total_bundles FROM bundles;"
```

## Expected Results

- **18 Menu Categories** (including sub-categories)
- **27 Bundles** across all categories:
  - 3 Triple Data Promo bundles
  - 3 Gwamon bundles
  - 5 YOLO Voice bundles
  - 3 Other Bundles
  - 3 DesaDe bundles
  - 3 FoLeva bundles
  - 2 Social Media bundles (WhatsApp, Facebook/Instagram)
  - 3 Daily bundles
  - 3 Weekly bundles
  - 3 Monthly bundles
  - 3 Hourly bundles

## Troubleshooting

### If insertion fails:
```bash
# Clear existing data
mysql -u root -P 3307 -h localhost mtn_ussd -e "SET FOREIGN_KEY_CHECKS=0; TRUNCATE TABLE bundles; TRUNCATE TABLE menu_categories; SET FOREIGN_KEY_CHECKS=1;"

# Then re-run insertion
mysql -u root -P 3307 -h localhost mtn_ussd < INSERT_ALL_DATA.sql
```

### Check what's in the database:
```bash
# List all categories
mysql -u root -P 3307 -h localhost mtn_ussd -e "SELECT id, code, name FROM menu_categories ORDER BY display_order;"

# List all bundles
mysql -u root -P 3307 -h localhost mtn_ussd -e "SELECT id, category_id, code, name, price FROM bundles ORDER BY category_id, display_order;"

# Bundles by category
mysql -u root -P 3307 -h localhost mtn_ussd -e "SELECT c.name AS category, COUNT(b.id) AS bundles FROM menu_categories c LEFT JOIN bundles b ON b.category_id = c.id WHERE c.parent_id IS NULL GROUP BY c.id, c.name ORDER BY c.display_order;"
```

## After Data Insertion

1. **Restart the Spring Boot application**
2. **Test USSD**: `curl -X POST "http://localhost:8080/ussd" -H "Content-Type: application/x-www-form-urlencoded" -d "sessionid=test&msidn=250788123456&input="`
3. **Test API**: `curl -X GET "http://localhost:8080/api/bundles"`
4. **Access Frontend**: Open `http://localhost:3000`

## Bundle ID Mapping

The system uses specific ID ranges for different categories:
- **1-3**: Triple Data Promo
- **4-6**: Gwamon
- **10-14**: YOLO Voice
- **20-22**: Other Bundles
- **30-32**: DesaDe
- **40-42**: FoLeva
- **50-51**: Social Media
- **60-62**: Daily Internet
- **70-72**: Weekly Internet
- **80-82**: Monthly Internet
- **90-92**: Hourly Internet

These IDs are hardcoded in `UssdController.java` for payment processing.
