package org.sam.lms.store.api.order.request

import jakarta.validation.constraints.Min

class CreateOrderRequest(
    @field:Min(1, message = "강의 아이디가 잘못되었습니다.")
    val courseId: Long
)