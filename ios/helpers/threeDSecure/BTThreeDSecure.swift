//
//  BTThreeDSecure.swift
//  expo-braintree
//
//  Created by OpenAI on 28/01/2026.
//
import Braintree
import Foundation

func prepareBTThreeDSecurePostalAddress(options: [String: Any]) -> BTThreeDSecurePostalAddress {
  let address = BTThreeDSecurePostalAddress()
  address.givenName = options["givenName"] as? String
  address.surname = options["surname"] as? String
  address.phoneNumber = options["phoneNumber"] as? String
  address.streetAddress = options["streetAddress"] as? String
  address.extendedAddress = options["extendedAddress"] as? String
  address.locality = options["locality"] as? String
  address.region = options["region"] as? String
  address.postalCode = options["postalCode"] as? String
  address.countryCodeAlpha2 = options["countryCodeAlpha2"] as? String
  return address
}

func prepareBTThreeDSecureRequest(options: [String: Any]) -> BTThreeDSecureRequest {
  let request = BTThreeDSecureRequest()
  if let amount = options["amount"] as? String {
    request.amount = NSDecimalNumber(string: amount)
  }
  request.nonce = options["nonce"] as? String
  request.email = options["email"] as? String
  request.mobilePhoneNumber = options["mobilePhoneNumber"] as? String
  if let challengeRequested = options["challengeRequested"] as? String {
    request.challengeRequested = getBoolValueByString(
      value: challengeRequested, defaultValue: false)
  }
  if let billingAddress = options["billingAddress"] as? [String: Any] {
    request.billingAddress = prepareBTThreeDSecurePostalAddress(options: billingAddress)
  } else if let billingAddress = options["billingAddress"] as? NSDictionary,
    let billingAddressDict = billingAddress as? [String: Any]
  {
    request.billingAddress = prepareBTThreeDSecurePostalAddress(options: billingAddressDict)
  }
  // Note: shippingAddress was removed from BTThreeDSecureRequest in recent Braintree SDK versions
  return request
}

func prepareBTThreeDSecureResult(result: BTThreeDSecureResult) -> NSDictionary {
  let payload = NSMutableDictionary()
  if let cardNonce = result.tokenizedCard {
    payload["nonce"] = cardNonce.nonce
    payload["cardNetwork"] = cardNonce.cardNetwork
    payload["lastFour"] = cardNonce.lastFour
    payload["lastTwo"] = cardNonce.lastTwo
    payload["expirationMonth"] = cardNonce.expirationMonth
    payload["expirationYear"] = cardNonce.expirationYear
    let threeDSecureInfo = cardNonce.threeDSecureInfo
    payload["liabilityShifted"] = threeDSecureInfo.liabilityShifted
    payload["liabilityShiftPossible"] = threeDSecureInfo.liabilityShiftPossible
    payload["status"] = String(describing: threeDSecureInfo.status)
  }
  return payload
}
