package org.sam.lms.store.api.payment

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.sam.lms.store.api.payment.request.PaymentFailureRequest
import org.sam.lms.store.api.payment.request.PaymentRequest
import org.sam.lms.store.api.payment.request.PaymentSuccessRequest
import org.sam.lms.store.api.payment.response.PaymentResponse
import org.sam.lms.store.domain.payment.application.PaymentService
import org.sam.lms.store.domain.payment.application.`in`.CreatePaymentDto
import org.sam.lms.store.domain.payment.application.`in`.PaymentFailureDto
import org.sam.lms.store.domain.payment.application.`in`.PaymentSuccessDto
import org.sam.lms.store.domain.provider.Provider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("store/api/v1/payments")
class PaymentController(private val paymentService: PaymentService) {

    @Operation(summary = "결제 페이지 호출")
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

    @Operation(summary = "결제 성공")
    @PostMapping("/success")
    fun completePayment(@Valid @RequestBody paymentSuccessRequest: PaymentSuccessRequest): ResponseEntity<Void> {
        val result = this.paymentService.complete(
            PaymentSuccessDto(
                orderNo = paymentSuccessRequest.orderNo,
                paymentKey = paymentSuccessRequest.paymentKey,
                resultCode = paymentSuccessRequest.resultCode
            )
        )
        //TODO: 결제 성공 페이지 응답
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "결제 실패")
    @PostMapping("/failure")
    fun failurePayment(@Valid @RequestBody paymentFailureRequest: PaymentFailureRequest): ResponseEntity<Void> {
        val result = this.paymentService.failure(
            PaymentFailureDto(
                orderNo = paymentFailureRequest.orderNo,
                paymentKey = paymentFailureRequest.paymentKey,
                errorCode = paymentFailureRequest.errorCode,
                errorMessage = paymentFailureRequest.errorMessage
            )
        )
        //TODO: 결제 실패 페이지 응답
        return ResponseEntity.ok().build()
    }

}