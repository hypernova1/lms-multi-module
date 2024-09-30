package org.sam.lms.store.domain.payment.application.`in`

data class PaymentFailureDto(val orderNo: String, val paymentKey: String, val errorCode: String, val errorMessage: String)