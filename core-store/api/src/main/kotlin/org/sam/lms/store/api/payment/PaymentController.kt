package org.sam.lms.store.api.payment

import jakarta.validation.Valid
import org.sam.lms.store.api.payment.request.PaymentRequest
import org.sam.lms.store.api.payment.response.PaymentResponse
import org.sam.lms.store.domain.payment.application.PaymentService
import org.sam.lms.store.domain.payment.application.`in`.CreatePaymentDto
import org.sam.lms.store.domain.provider.Provider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("store/api/v1/payments")
class PaymentController(private val paymentService: PaymentService) {

    @PostMapping
    fun createPayment(
        @Valid @RequestBody paymentRequest: PaymentRequest,
        provider: Provider
    ): ResponseEntity<PaymentResponse> {
        val result = this.paymentService.create(
            CreatePaymentDto(
                orderNo = paymentRequest.orderNo,
                type = paymentRequest.type,
                paidPrice = paymentRequest.paidPrice
            ),
            provider
        )
        return ResponseEntity.ok(PaymentResponse(result))
    }

}