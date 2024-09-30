package org.sam.lms.store.api.payment.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.sam.lms.store.domain.payment.domain.PaymentType

data class PaymentRequest(
    @field:Schema(description = "주문번호", example = "202409301230", required = true)
    @field:NotBlank(message = "주문번호가 누락되었습니다.")
    val orderNo: String = "",

    @field:Schema(description = "결제 금액", example = "1000", required = true)
    @Min(100, message = "결제 금액은 100원 이상입니다.")
    val paidPrice: Int = 0,

    @field:Schema(description = "결제 타입", allowableValues = ["CARD", "ACCOUNT"], example = "CARD", required = true)
    @field:NotNull(message = "결제 타입은 필수입니다.")
    val type: PaymentType = PaymentType.CARD
)