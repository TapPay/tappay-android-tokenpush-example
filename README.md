Tappay Push Token Sample
==========================

  - [Flow](#flow)
  - [Introduction](#introduction)
  - [Getting Started](#getting-started)
<br>
<br>

## Flow
![](./TSP_Shop_Bind_Card_Flow.png)  
<br>
<br>

## Introduction
1. Make sure issuer app has been installed
2. Issuer App will launch your App 
3. getTspPushToken and cancelUrl from intent data  
<br>
<br>

## Getting Started  
<br>

Fill in partner_key (from tappay portal)  

```java
// string.xml
<string name="partner_key">your partner key</string>
```
<br>

TokenPushTask.java will show you how to send a HTTP POST request to TapPay 

```java

...

// JSON request
JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("partner_key", partnerKey);
            jsonRequest.put("tsp_push_token", tokenPushParam.getTspToken());
        } catch (JSONException e) {
            Log.e(TAG, "init TokenPushTask error: " + Log.getStackTraceString(e));
}

 // onPostExecute , Register a callback if you need to Show Add Card Result in your UI component (MainActivity here)

tokenPushParam.getTokenPushCallback().getTaskResult(jsonObject);
    
```

