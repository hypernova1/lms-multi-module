package org.sam.lms.store.api.cart.request

import jakarta.validation.constraints.Min

class AddCartProductRequest(
    @field:Min(1, message = "강의 아이디는 0 이하일 수 없습니다.")
    val courseId: Long = 0L,
)