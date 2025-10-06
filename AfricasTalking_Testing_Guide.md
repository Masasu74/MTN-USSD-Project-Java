# AfricasTalking USSD Testing Guide

## 🚀 Why AfricasTalking is Great for Testing

- **Free Sandbox Environment**: No cost for testing
- **Real Network Simulation**: Tests with actual telecom networks
- **Comprehensive Dashboard**: Monitor sessions and debug easily
- **Built-in Simulator**: No need for external tools
- **Production Ready**: Same platform used by real telecom operators

## 📋 Step-by-Step Setup

### Step 1: Create AfricasTalking Account
1. **Go to**: https://account.africastalking.com/apps/sandbox
2. **Sign up** for a free account
3. **Verify your email** and complete registration
4. **Login** to your dashboard

### Step 2: Set Up USSD Channel
1. **Navigate to**: USSD → Create Channel
2. **Configure your channel**:
   ```
   Channel Number: *384*1300# (or any available number)
   Callback URL: https://9838e5d79eaf.ngrok-free.app/ussd
   HTTP Method: POST
   ```

### Step 3: Configure Your Application
Your current USSD endpoint is already compatible with AfricasTalking! The request format matches perfectly.

## 🧪 Testing with AfricasTalking Simulator

### Step 1: Access the Simulator
1. **Go to**: https://simulator.africastalking.com:1517/
2. **Login** with your AfricasTalking credentials
3. **Select your USSD channel** (e.g., *384*1300#)

### Step 2: Test the Complete Flow

#### 2.1 Initial Request
- **Dial**: `*384*1300#`
- **Expected Response**: Your bundle menu

#### 2.2 Bundle Selection
- **Select**: `1` (for Gwamon' Weekend)
- **Expected Response**: Payment options

#### 2.3 Payment Selection
- **Select**: `1` (for Airtime)
- **Expected Response**: Purchase confirmation

## 🔧 AfricasTalking Request Format

AfricasTalking sends requests in this format:
```
POST https://9838e5d79eaf.ngrok-free.app/ussd
Content-Type: application/x-www-form-urlencoded

sessionId=ATUid_1234567890abcdef&phoneNumber=+254700000000&text=&serviceCode=*384*1300#
```

### Request Parameters:
- **sessionId**: Unique session identifier (e.g., `ATUid_1234567890abcdef`)
- **phoneNumber**: Phone number in international format (e.g., `+254700000000`)
- **text**: User input (empty for initial request)
- **serviceCode**: The USSD code (e.g., `*384*1300#`)

### Response Format (Your app already returns this):
```json
{
  "message": "Your USSD response text",
  "sessionState": "CON" // or "END"
}
```

## 📱 Expected USSD Flow

```
*384*1300# → Send
↓
MTN YOLO USSD
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
Thank you for using MTN YOLO USSD!
```

## 🔍 Monitoring and Debugging

### AfricasTalking Dashboard
1. **Go to**: https://account.africastalking.com/apps/sandbox
2. **Navigate to**: USSD → Sessions
3. **View**: Real-time session logs and responses

### Server Logs
Your application already logs all USSD requests:
```
USSD Request - Session: ATUid_1234567890abcdef, Phone: +254700000000, Text: 1
```

### ngrok Web Interface
- **URL**: http://localhost:4040
- **View**: All incoming requests and responses

## 🚨 Troubleshooting

### Common Issues:

#### 1. "Callback URL Not Accessible"
- **Check**: Is ngrok running? `curl -s http://localhost:4040/api/tunnels`
- **Check**: Is your server running? `lsof -i :8080`
- **Solution**: Restart both server and ngrok

#### 2. "Invalid Response Format"
- **Check**: Response includes both `message` and `sessionState` fields
- **Check**: Content-Type is `application/json`
- **Solution**: Verify your UssdResponse class

#### 3. "Session Timeout"
- **Check**: Session ID is consistent across requests
- **Check**: Response includes `sessionState: "CON"` for continuing sessions
- **Solution**: Use same session ID for related requests

#### 4. "Database Connection Error"
- **Check**: MySQL is running on port 3307
- **Check**: Database `mtn_ussd` exists
- **Solution**: Start MySQL and verify connection

## 🎯 Testing Checklist

### ✅ Pre-Test Setup:
- [ ] AfricasTalking account created
- [ ] USSD channel configured
- [ ] ngrok tunnel active
- [ ] Server running on port 8080
- [ ] Database connection working

### ✅ Test Scenarios:
- [ ] **Initial Request**: Empty text returns main menu
- [ ] **Bundle Selection**: Valid numbers (1-4) show payment options
- [ ] **Invalid Selection**: Invalid numbers show error message
- [ ] **Payment Selection**: Valid methods (1-2) process purchase
- [ ] **Go Back**: Option 0 returns to previous menu
- [ ] **Session Management**: Same session ID maintains state

### ✅ Expected Behaviors:
- [ ] Menu displays all active bundles
- [ ] Weekend bundles only available Friday-Sunday
- [ ] Purchase creates database record
- [ ] Confirmation message shows correct bundle and payment method
- [ ] Session ends after successful purchase

## 🔗 Useful Links

- **AfricasTalking Sandbox**: https://account.africastalking.com/apps/sandbox
- **USSD Simulator**: https://simulator.africastalking.com:1517/
- **USSD API Documentation**: https://africastalking.com/ussd
- **ngrok Dashboard**: http://localhost:4040
- **Your ngrok URL**: https://9838e5d79eaf.ngrok-free.app

## 💡 Pro Tips

1. **Use Real Phone Numbers**: AfricasTalking simulator accepts real phone numbers for more realistic testing
2. **Monitor Sessions**: Use the dashboard to track session flow and debug issues
3. **Test Edge Cases**: Try invalid inputs, session timeouts, and network interruptions
4. **Production Ready**: The same endpoint can be used for production deployment

## 🚀 Next Steps

1. **Create your AfricasTalking account**
2. **Set up the USSD channel** with your ngrok URL
3. **Test with the simulator**
4. **Monitor sessions** in the dashboard
5. **Debug any issues** using the logs

Your USSD application is already perfectly compatible with AfricasTalking! 🎉

