# Usage
# Package Version 3.x.x
## Request One Time Payment

```javascript
import {
  requestOneTimePayment,
} from "expo-braintree";

const result: BTPayPalAccountNonceResult | BTPayPalError  = await requestOneTimePayment({
        clientToken: 'Token",
        merchantAppLink: "https://braintree-example-app.web.app",
        amount: '5.0',
        currencyCode: 'USD'
    })

```

## Card tokenization
```javascript
import {
  tokenizeCard,
} from "expo-braintree";

const result: BTCardTokenizationNonceResult | BTPayPalError = await tokenizeCard({
        clientToken: 'Token",
        number: '1111222233334444',
        expirationMonth: '11',
        expirationYear: '24',
        cvv: '123',
        postalCode: '',
    })

```

## 3D Secure card verification
```javascript
import {
  requestThreeDSecureVerification,
  BoolValue,
} from "expo-braintree";

const threeDSecureResult = await requestThreeDSecureVerification({
  clientToken: 'Token',
  nonce: 'card-nonce-from-tokenize',
  amount: '10.00',
  email: 'customer@example.com',
  challengeRequested: BoolValue.true,
  billingAddress: {
    givenName: 'Jill',
    surname: 'Doe',
    phoneNumber: '5551234567',
    streetAddress: '555 Smith St',
    extendedAddress: '#5',
    locality: 'Oakland',
    region: 'CA',
    postalCode: '12345',
    countryCodeAlpha2: 'US',
  },
});
```

## Request PayPal billing agreement
```javascript
import {
  requestBillingAgreement,
} from "expo-braintree";

const result: BTPayPalAccountNonceResult | BTPayPalError  = await requestBillingAgreement({
        clientToken: 'Token",
        merchantAppLink: "https://braintree-example-app.web.app",
    })
    .then(result => console.log(result))
    .catch((error) => console.log(error));
```
## Call Data Collector and get correlation id
```javascript
import {
  getDeviceDataFromDataCollector,
} from "expo-braintree";
const result: string = await getDeviceDataFromDataCollector(clientToken)
```

## Get Venmo Nonce
```javascript
import {
  requestVenmoNonce,
} from "expo-braintree";

const nonce = await requestVenmoNonce({
    clientToken,
    vault: BoolValue.true,
    paymentMethodUsage: BTVenmoPaymntMethodUsage.multiUse,
    totalAmount: '5',
});
```
