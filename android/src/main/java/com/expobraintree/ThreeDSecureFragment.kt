package com.expobraintree

import androidx.fragment.app.Fragment
import com.braintreepayments.api.threedsecure.ThreeDSecureLauncher
import com.braintreepayments.api.threedsecure.ThreeDSecurePaymentAuthRequest
import com.braintreepayments.api.threedsecure.ThreeDSecurePaymentAuthResult

class ThreeDSecureFragment : Fragment() {
  private var callback: ((ThreeDSecurePaymentAuthResult) -> Unit)? = null
  private val launcher = ThreeDSecureLauncher(this) { paymentAuthResult ->
    callback?.invoke(paymentAuthResult)
  }

  fun setCallback(callback: (ThreeDSecurePaymentAuthResult) -> Unit) {
    this.callback = callback
  }

  fun clearCallback() {
    callback = null
  }

  fun launch(request: ThreeDSecurePaymentAuthRequest.ReadyToLaunch) {
    launcher.launch(request)
  }
}
