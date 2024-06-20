# portone

This API is designed to handle Stripe payments, including creating payment intents, capturing payment intents, creating refunds, and retrieving payment intents. The API is built using Spring Boot and is hosted on Railway.
<br/>
Base URL
<br/>
https://portone-production.up.railway.app/api/v1
<br/>
Endpoints
<br/>
1. Create Payment Intent
<br/>
URL: /create_intent
<br/>

Method: POST
<br/>
Description: Creates a new payment intent.
<br/>
{
<br/>
  "amount": 10000,
<br/>
  "currency": "inr"
<br/>
}
<br/>

Response
<br/>

{
    <br/>
    "clientSecret": "akeyyourecive",
    <br/>
    "id": "pi_3PTeYtGHXAeeKhyd00Qkwim0",
    <br/>
    "status": "requires_capture"
    <br/>
}
<br/>

2. Capture Payment Intent
<br/>
URL: /capture_intent/{id}
<br/>

Method: POST
<br/>
Description: Captures a previously created payment intent.
<br/>

Path Variable:
<br/>

id: The ID of the payment intent to capture.
<br/>

Response
<br/>
{
    <br/>
    "status": "succeeded"
    <br/>
}
<br/>

3. Create Refund
<br/>
URL: /create_refund/{id}
<br/>
Method: POST
<br/>
Description: Creates a refund for a specific payment intent.
<br/>
Path Variable:
<br/>
id: The ID of the payment intent to refund.

<br/>
Response
<br/>
{
    <br/>
    "status": "succeeded"
    <br/>
}
<br/>

4. Get Payment Intents
<br/>
URL: /get_intents
<br/>
Method: GET
<br/>
Description: Retrieves a list of all payment intents.
<br/>

Response
<br/>
[
    <br/>
  {
    <br/>
    "id": "pi_1Fxxxxxxxxxxxx",
    <br/>
    "status": "succeeded",
    <br/>
    "amount": 1000,
    <br/>
    "currency": "usd",
    <br/>
    "paymentMethodTypes": ["card"]
    <br/>
  },
  <br/>
  {
    "id": "pi_2Fxxxxxxxxxxxx",
    "status": "requires_payment_method",
    "amount": 2000,
    "currency": "eur",
    "paymentMethodTypes": ["card"]
  }
  <br/>
]
<br/>

