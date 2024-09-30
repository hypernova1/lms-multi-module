package org.sam.lms.store.api.payment.response

import org.sam.lms.store.domain.payment.domain.Payment
import org.sam.lms.store.domain.payment.domain.PaymentType

data class PaymentResponse(val orderNo: String, val paidPrice: Int, val type: PaymentType) {
    constructor(payment: Payment) : this(orderNo = payment.orderNo, paidPrice = payment.paidPrice, type = payment.type)

}