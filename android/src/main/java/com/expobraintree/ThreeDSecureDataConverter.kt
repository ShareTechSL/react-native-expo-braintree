package com.expobraintree

import com.braintreepayments.api.card.CardNonce
import com.braintreepayments.api.core.PaymentMethodNonce
import com.braintreepayments.api.threedsecure.ThreeDSecureAdditionalInformation
import com.braintreepayments.api.threedsecure.ThreeDSecurePostalAddress
import com.braintreepayments.api.threedsecure.ThreeDSecureRequest
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap

class ThreeDSecureDataConverter {
  companion object {
    fun createThreeDSecureRequest(options: ReadableMap): ThreeDSecureRequest {
      val request = ThreeDSecureRequest()
      request.amount = options.getString("amount") ?: ""
      request.nonce = options.getString("nonce") ?: ""

      if (options.hasKey("email")) request.email = options.getString("email")
      if (options.hasKey("mobilePhoneNumber")) {
        request.mobilePhoneNumber = options.getString("mobilePhoneNumber")
      }

      if (options.hasKey("challengeRequested")) {
        val challengeRequested: String = options.getString("challengeRequested") ?: ""
        when (challengeRequested) {
          "true" -> request.challengeRequested = true
          "false" -> request.challengeRequested = false
        }
      }

      if (options.hasKey("billingAddress")) {
        val billingAddress = options.getMap("billingAddress")
        if (billingAddress != null) {
          request.billingAddress = createThreeDSecurePostalAddress(billingAddress)
        }
      }

      if (options.hasKey("shippingAddress")) {
        val shippingAddress = options.getMap("shippingAddress")
        if (shippingAddress != null) {
          val additionalInfo =
            request.additionalInformation ?: ThreeDSecureAdditionalInformation()
          additionalInfo.shippingAddress = createThreeDSecurePostalAddress(shippingAddress)
          request.additionalInformation = additionalInfo
        }
      }

      return request
    }

    private fun createThreeDSecurePostalAddress(address: ReadableMap): ThreeDSecurePostalAddress {
      val result = ThreeDSecurePostalAddress()
      if (address.hasKey("givenName")) result.givenName = address.getString("givenName")
      if (address.hasKey("surname")) result.surname = address.getString("surname")
      if (address.hasKey("phoneNumber")) result.phoneNumber = address.getString("phoneNumber")
      if (address.hasKey("streetAddress")) result.streetAddress = address.getString("streetAddress")
      if (address.hasKey("extendedAddress")) result.extendedAddress = address.getString("extendedAddress")
      if (address.hasKey("locality")) result.locality = address.getString("locality")
      if (address.hasKey("region")) result.region = address.getString("region")
      if (address.hasKey("postalCode")) result.postalCode = address.getString("postalCode")
      if (address.hasKey("countryCodeAlpha2")) {
        result.countryCodeAlpha2 = address.getString("countryCodeAlpha2")
      }
      return result
    }

    fun createThreeDSecureResult(nonce: PaymentMethodNonce): WritableMap {
      val result: WritableMap = Arguments.createMap()
      result.putString("nonce", nonce.string)

      if (nonce is CardNonce) {
        if (nonce.cardType == "Unknown") {
          result.putString("cardNetwork", "")
        } else {
          result.putString("cardNetwork", nonce.cardType)
        }
        result.putString("lastFour", nonce.lastFour)
        result.putString("lastTwo", nonce.lastTwo)
        result.putString("expirationMonth", nonce.expirationMonth)
        result.putString("expirationYear", nonce.expirationYear)

        val threeDSecureInfo = nonce.threeDSecureInfo
        if (threeDSecureInfo != null) {
          result.putBoolean("liabilityShifted", threeDSecureInfo.liabilityShifted)
          result.putBoolean("liabilityShiftPossible", threeDSecureInfo.liabilityShiftPossible)
          result.putString("status", threeDSecureInfo.status.toString())
        }
      }

      return result
    }
  }
}
