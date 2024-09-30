package org.sam.lms.store.domain.order.domain

interface PaymentGateway {
    fun confirm(paymentKey: String)
    fun cancel(paymentKey: String, amount: Int, reason: String?)
}