package org.sam.lms.store.domain.payment.domain

interface PaymentRepository {
    fun save(payment: Payment)
}