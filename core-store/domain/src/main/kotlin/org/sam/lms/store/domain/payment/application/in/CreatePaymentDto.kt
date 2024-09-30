package org.sam.lms.store.domain.payment.application.`in`

import org.sam.lms.store.domain.payment.domain.PaymentType

class CreatePaymentDto(val orderNo: String, val type: PaymentType, val paidPrice: Int)