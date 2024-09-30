package org.sam.lms.store.domain.payment.domain

import org.springframework.data.jpa.repository.Query

interface PaymentRepository {
    fun save(orderNo: Payment): Payment

    @Query("SELECT p FROM Payment p JOIN FETCH p.logs log WHERE p.orderNo = :orderNo")
    fun findByOrderNo(orderNo: String): Payment?
}