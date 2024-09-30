package org.sam.lms.store.domain.payment.domain

interface PaymentRepository {
    fun save(orderNo: Payment): Payment
    fun findByOrderNo(orderNo: String): Payment?
}