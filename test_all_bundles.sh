#!/bin/bash

echo "🧪 Testing All Placeholder Bundle Amounts..."
echo "=============================================="

# Test Bundle IDs with expected amounts
declare -A bundle_tests=(
    ["100"]="100.00"  # Daily Bundle 1
    ["101"]="200.00"  # Daily Bundle 2
    ["102"]="500.00"  # Weekly Bundle 1
    ["103"]="1000.00" # Weekly Bundle 2
    ["111"]="200.00"  # WhatsApp Bundle
    ["112"]="200.00"  # Facebook/Instagram Bundle
)

# Function to test a bundle
test_bundle() {
    local bundle_id=$1
    local expected_amount=$2
    local phone_num="250788${bundle_id}"
    local session_id="test_bundle_${bundle_id}_$(date +%s)"
    
    echo "Testing Bundle ID $bundle_id (Expected: $expected_amount)..."
    
    # Create session
    curl -s -X POST http://localhost:8080/ussd \
        -H "Content-Type: application/x-www-form-urlencoded" \
        -d "sessionid=$session_id&msidn=$phone_num&input=" > /dev/null
    
    # Navigate to YOLO Internet
    curl -s -X POST http://localhost:8080/ussd \
        -H "Content-Type: application/x-www-form-urlencoded" \
        -d "sessionid=$session_id&msidn=$phone_num&input=2" > /dev/null
    
    # Select appropriate category based on bundle ID
    if [[ $bundle_id -ge 100 && $bundle_id -le 101 ]]; then
        # Daily bundles
        curl -s -X POST http://localhost:8080/ussd \
            -H "Content-Type: application/x-www-form-urlencoded" \
            -d "sessionid=$session_id&msidn=$phone_num&input=1" > /dev/null
    elif [[ $bundle_id -ge 102 && $bundle_id -le 103 ]]; then
        # Weekly bundles
        curl -s -X POST http://localhost:8080/ussd \
            -H "Content-Type: application/x-www-form-urlencoded" \
            -d "sessionid=$session_id&msidn=$phone_num&input=2" > /dev/null
    elif [[ $bundle_id -ge 111 && $bundle_id -le 112 ]]; then
        # Social bundles
        curl -s -X POST http://localhost:8080/ussd \
            -H "Content-Type: application/x-www-form-urlencoded" \
            -d "sessionid=$session_id&msidn=$phone_num&input=3" > /dev/null
    fi
    
    # Select bundle based on ID
    if [[ $bundle_id -eq 100 || $bundle_id -eq 102 || $bundle_id -eq 111 ]]; then
        bundle_choice=1
    else
        bundle_choice=2
    fi
    
    # Select bundle and complete purchase
    curl -s -X POST http://localhost:8080/ussd \
        -H "Content-Type: application/x-www-form-urlencoded" \
        -d "sessionid=$session_id&msidn=$phone_num&input=$bundle_choice" > /dev/null
    
    # Complete with MoMo payment
    curl -s -X POST http://localhost:8080/ussd \
        -H "Content-Type: application/x-www-form-urlencoded" \
        -d "sessionid=$session_id&msidn=$phone_num&input=2" > /dev/null
    
    # Check database result
    sleep 2
    local actual_amount=$(/Applications/XAMPP/xamppfiles/bin/mysql -uroot -P3307 -h127.0.0.1 mtn_ussd -e "SELECT amount FROM purchases WHERE phone_number='$phone_num' AND bundle_id=$bundle_id ORDER BY purchased_at DESC LIMIT 1;" 2>/dev/null | tail -1)
    
    if [[ "$actual_amount" == "$expected_amount" ]]; then
        echo "✅ Bundle ID $bundle_id: $actual_amount (CORRECT)"
    else
        echo "❌ Bundle ID $bundle_id: $actual_amount (Expected: $expected_amount)"
    fi
    echo ""
}

# Run tests for each bundle
for bundle_id in "${!bundle_tests[@]}"; do
    test_bundle "$bundle_id" "${bundle_tests[$bundle_id]}"
done

echo "🏁 All bundle tests completed!"
echo "Checking final database state..."
/Applications/XAMPP/xamppfiles/bin/mysql -uroot -P3307 -h127.0.0.1 mtn_ussd -e "SELECT bundle_id, amount, COUNT(*) as count FROM purchases WHERE bundle_id IN (100,101,102,103,111,112) GROUP BY bundle_id, amount ORDER BY bundle_id;" 2>&1







