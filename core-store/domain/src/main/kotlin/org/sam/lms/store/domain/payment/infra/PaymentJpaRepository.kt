package org.sam.lms.store.domain.payment.infra

import org.sam.lms.store.domain.payment.domain.Payment
import org.sam.lms.store.domain.payment.domain.PaymentRepository
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<Payment, Long>, PaymentRepository {
}