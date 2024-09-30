package org.sam.lms.store.api.payment.request

class PaymentSuccessRequest(val orderNo: String, val paymentKey: String, val resultCode: String)