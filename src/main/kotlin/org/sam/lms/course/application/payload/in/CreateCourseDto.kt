package org.sam.lms.course.application.payload.`in`

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.sam.lms.address.application.payload.`in`.AddressRequest
import org.sam.lms.course.domain.CourseType

data class CreateCourseDto(
    @field:Schema(description = "제목", example = "한 눈에 보는 웹 강의", required = true)
    @field:NotBlank(message = "제목은 필수입니다.")
    val title: String = "",

    @field:Schema(description = "상세 설명", example = "웹을 한눈에 보고 배우는 강의입니다.", required = true)
    @field:NotBlank(message = "상세 설명은 필수입니다.")
    val description: String = "",

    @field:Schema(description = "카테고리 아이디", example = "1", required = true)
    @field:NotBlank(message = "상세 설명은 필수입니다.")
    val categoryId: Long = 0,

    @field:Schema(description = "강의 가격", example = "1000", required = true)
    @field:Min(100, message = "가격은 100원 이상입니다.")
    val price: Int = 0,

    @field:Schema(description = "강의 타입", example = "1000", allowableValues = ["ONLINE", "OFFLINE"], required = true)
    @field:NotBlank(message = "강의 타입은 필수입니다.")
    val type: CourseType = CourseType.ONLINE,

    @field:Schema(description = "최대 수강 가능 인원 (오프라인 강의일시)", example = "10", required = false)
    @field:Min(1, message = "최대 수강 가능 인원은 최소 한명 이상입니다.")
    val maxEnrollment: Int = 0,

    @field:Schema(description = "오프라인 강의 주소 (오프라인 강의일시", required = false)
    val address: AddressRequest? = null
)