# Arkesel USSD Simulator Testing Guide

## 🚀 Quick Setup Steps

### Step 1: Access Arkesel Simulator
1. **Go to**: https://simulator.arkesel.com/
2. **Sign up** for a free account (if you don't have one)
3. **Login** to your account

### Step 2: Configure the Simulator
1. **Click on "Create New Simulation"** or "New Test"
2. **Fill in the configuration**:

#### Basic Configuration:
- **Service Name**: `MTN Gwamon Bundles`
- **USSD Code**: `*154#`
- **Endpoint URL**: `https://9838e5d79eaf.ngrok-free.app/ussd`
- **HTTP Method**: `POST`
- **Content Type**: `application/x-www-form-urlencoded`

#### Network Settings:
- **Network**: `MTN` (or any available network)
- **Country**: `Ghana` (or your preferred country)
- **Phone Number**: `233241234567` (or any test number)

#### Request Configuration:
- **Session ID**: `test_session_123`
- **Phone Number**: `233241234567`
- **Service Code**: `*154#`
- **Text**: (leave empty for initial request)

### Step 3: Test the USSD Flow

#### 3.1 Initial Request (Show Menu)
**Request Parameters:**
```
sessionId: test_session_123
phoneNumber: 233241234567
text: (empty)
serviceCode: *154#
```

**Expected Response:**
```json
{
  "message": "MTN Gwamon Bundles\nValid till Sunday 23:59\n\n1) Gwamon' Weekend\n2) 500FRW=0MB+700Mins+30SMS\n3) 1000FRW=7GB+30SMS\n4) 1500FRW=8GB+800Mins+30SMS\n0) Go back",
  "sessionState": "CON"
}
```

#### 3.2 Bundle Selection (Select Bundle 1)
**Request Parameters:**
```
sessionId: test_session_123
phoneNumber: 233241234567
text: 1
serviceCode: *154#
```

**Expected Response:**
```json
{
  "message": "Pay Gwamon' Weekend via:\n1. Airtime\n2. MoMo\n0. Go back",
  "sessionState": "CON"
}
```

#### 3.3 Payment Selection (Select Airtime)
**Request Parameters:**
```
sessionId: test_session_123
phoneNumber: 233241234567
text: 1*1
serviceCode: *154#
```

**Expected Response:**
```json
{
  "message": "Purchase successful!\nGwamon' Weekend purchased via Airtime.\nThank you for using MTN Gwamon!",
  "sessionState": "END"
}
```

## 🔧 Alternative Configuration (If Arkesel has different fields)

### If Arkesel uses different parameter names:
- **sessionId** → **session_id** or **session**
- **phoneNumber** → **phone_number** or **msisdn**
- **text** → **user_input** or **input**
- **serviceCode** → **service_code** or **ussd_code**

### If Arkesel expects different request format:
Some simulators expect the request body in different formats:

#### JSON Format (if needed):
```json
{
  "sessionId": "test_session_123",
  "phoneNumber": "233241234567",
  "text": "",
  "serviceCode": "*154#"
}
```

#### Form Data Format:
```
sessionId=test_session_123&phoneNumber=233241234567&text=&serviceCode=*154#
```

## 🧪 Testing Checklist

### ✅ Pre-Test Verification:
- [ ] Your Spring Boot server is running on port 8080
- [ ] ngrok tunnel is active and accessible
- [ ] Database connection is working
- [ ] USSD endpoint responds to local requests

### ✅ Test Scenarios:
- [ ] **Initial Request**: Empty text returns main menu
- [ ] **Bundle Selection**: Valid bundle numbers (1-4) show payment options
- [ ] **Invalid Selection**: Invalid numbers show error message
- [ ] **Payment Selection**: Valid payment methods (1-2) process purchase
- [ ] **Go Back**: Option 0 returns to previous menu
- [ ] **Session Management**: Same session ID maintains state

### ✅ Expected Behaviors:
- [ ] Menu displays all active bundles
- [ ] Weekend bundles only available Friday-Sunday
- [ ] Purchase creates database record
- [ ] Confirmation message shows correct bundle and payment method
- [ ] Session ends after successful purchase

## 🚨 Troubleshooting

### Common Issues:

#### 1. "Connection Refused" Error:
- **Check**: Is your server running? `lsof -i :8080`
- **Check**: Is ngrok tunnel active? Visit `http://localhost:4040`
- **Solution**: Restart server and ngrok

#### 2. "Invalid Response Format" Error:
- **Check**: Response is valid JSON with `message` and `sessionState` fields
- **Check**: Content-Type header is `application/json`
- **Solution**: Verify your UssdResponse class structure

#### 3. "Session Timeout" Error:
- **Check**: Session ID is consistent across requests
- **Check**: Response includes `sessionState: "CON"` for continuing sessions
- **Solution**: Use same session ID for related requests

#### 4. "Database Error":
- **Check**: MySQL is running on port 3307
- **Check**: Database `mtn_ussd` exists
- **Check**: Tables `bundles` and `purchases` exist
- **Solution**: Start MySQL and run migrations

## 📱 Complete USSD Flow Test

### Test Sequence:
1. **Start**: Dial `*154#`
2. **Menu**: See bundle options
3. **Select**: Choose bundle 1
4. **Payment**: Choose Airtime (option 1)
5. **Confirm**: See purchase success message

### Expected User Experience:
```
*154# → Send
↓
MTN Gwamon Bundles
Valid till Sunday 23:59

1) Gwamon' Weekend
2) 500FRW=0MB+700Mins+30SMS
3) 1000FRW=7GB+30SMS
4) 1500FRW=8GB+800Mins+30SMS
0) Go back

[User selects 1]
↓
Pay Gwamon' Weekend via:
1. Airtime
2. MoMo
0. Go back

[User selects 1]
↓
Purchase successful!
Gwamon' Weekend purchased via Airtime.
Thank you for using MTN Gwamon!
```

## 🔗 Useful Links

- **Arkesel Simulator**: https://simulator.arkesel.com/
- **Arkesel Documentation**: https://developers.arkesel.com/
- **ngrok Dashboard**: https://dashboard.ngrok.com/
- **Your ngrok URL**: https://9838e5d79eaf.ngrok-free.app

## 📞 Support

If you encounter issues:
1. Check the server logs for errors
2. Verify the ngrok tunnel is working
3. Test with Postman first
4. Check the Arkesel documentation for specific requirements

