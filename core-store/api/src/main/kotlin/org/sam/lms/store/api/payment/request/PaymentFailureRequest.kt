package org.sam.lms.store.api.payment.request

class PaymentFailureRequest(
    val orderNo: String,
    val paymentKey: String,
    val errorCode: String,
    val errorMessage: String
)