package org.sam.lms.store.domain.payment.application

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.store.domain.order.application.OrderService
import org.sam.lms.store.domain.payment.application.`in`.CreatePaymentDto
import org.sam.lms.store.domain.payment.domain.Payment
import org.sam.lms.store.domain.payment.domain.PaymentRepository
import org.sam.lms.store.domain.provider.Provider
import org.springframework.stereotype.Service

@Service
class PaymentService(private val paymentRepository: PaymentRepository, private val orderService: OrderService) {

    fun create(createPaymentDto: CreatePaymentDto, provider: Provider): Payment {
        val order = orderService.findOne(createPaymentDto.orderNo) ?: throw NotFoundException(ErrorCode.ORDER_NOT_FOUND)
        val payment = Payment.from(createPaymentDto, provider)
        paymentRepository.save(payment)
        return payment
    }
}