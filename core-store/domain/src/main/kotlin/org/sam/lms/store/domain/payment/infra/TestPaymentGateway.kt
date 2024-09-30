package org.sam.lms.store.domain.payment.infra

import org.sam.lms.store.domain.order.domain.PaymentGateway
import org.springframework.stereotype.Component

@Component
class TestPaymentGateway : PaymentGateway {

    override fun cancel(paymentKey: String, amount: Int, reason: String?) {
    }

    override fun confirm(paymentKey: String) {
    }
}