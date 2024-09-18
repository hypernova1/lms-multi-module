package org.sam.lms.domain.address.application.payload.`in`

import io.swagger.v3.oas.annotations.media.Schema

class AddressRequest(
    @field:Schema(description = "아이디 (주소 정보 수정시)", required = false)
    val id: Long? = null,

    @field:Schema(description = "시", required = true)
    val si: String = "",

    @field:Schema(description = "구/군", required = true)
    val gugun: String = "",

    @field:Schema(description = "도로명 주소", required = false)
    val doro: String = "",

    @field:Schema(description = "상세 주소", required = false)
    val detail: String = "",

    @field:Schema(description = "우편 번호", required = true)
    val zipcode: String = ""
)