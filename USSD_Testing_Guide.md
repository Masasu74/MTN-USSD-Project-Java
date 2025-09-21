# USSD Testing Guide

## Local Testing with Postman

### 1. Initial Request (Show Menu)
```
POST http://localhost:8080/ussd
Content-Type: application/x-www-form-urlencoded

sessionId=test123&phoneNumber=0788317222&text=&serviceCode=*154#
```

**Expected Response:**
```json
{
  "message": "MTN Gwamon Bundles\nValid till Sunday 23:59\n\n1) Gwamon' Weekend\n2) 500FRW=0MB+700Mins+30SMS\n3) 1000FRW=7GB+30SMS\n4) 1500FRW=8GB+800Mins+30SMS\n0) Go back",
  "sessionState": "CON"
}
```

### 2. Bundle Selection (Select Bundle 1)
```
POST http://localhost:8080/ussd
Content-Type: application/x-www-form-urlencoded

sessionId=test123&phoneNumber=0788317222&text=1&serviceCode=*154#
```

**Expected Response:**
```json
{
  "message": "Pay Gwamon' Weekend via:\n1. Airtime\n2. MoMo\n0. Go back",
  "sessionState": "CON"
}
```

### 3. Payment Selection (Select Airtime)
```
POST http://localhost:8080/ussd
Content-Type: application/x-www-form-urlencoded

sessionId=test123&phoneNumber=0788317222&text=1*1&serviceCode=*154#
```

**Expected Response:**
```json
{
  "message": "Purchase successful!\nGwamon' Weekend purchased via Airtime.\nThank you for using MTN Gwamon!",
  "sessionState": "END"
}
```

## Alternative Simulators

### 1. USSD.dev (Recommended)
- URL: https://ussd.dev/
- No authentication needed
- Works with localhost
- Simple and reliable

### 2. Arkesel Simulator
- URL: https://simulator.arkesel.com/
- Free tier available
- Good documentation

### 3. Local Testing Commands

#### Test with curl:
```bash
# Initial request
curl -X POST http://localhost:8080/ussd \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "sessionId=test123&phoneNumber=0788317222&text=&serviceCode=*154#"

# Bundle selection
curl -X POST http://localhost:8080/ussd \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "sessionId=test123&phoneNumber=0788317222&text=1&serviceCode=*154#"

# Payment selection
curl -X POST http://localhost:8080/ussd \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "sessionId=test123&phoneNumber=0788317222&text=1*1&serviceCode=*154#"
```

## Troubleshooting

1. **Make sure your server is running**: `lsof -i :8080`
2. **Check server logs** for any errors
3. **Verify database connection** (MySQL on port 3307)
4. **Test with Postman first** before using simulators

## USSD Flow Summary

1. User dials `*154#`
2. System shows bundle menu
3. User selects bundle (1-4)
4. System shows payment options
5. User selects payment method (1=Airtime, 2=MoMo)
6. System processes purchase and shows confirmation

