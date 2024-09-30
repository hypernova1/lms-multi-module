package org.sam.lms.api.course.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

class ReviewRequest(
    @field:NotBlank(message = "내용은 필수입니다")
    @field:Schema(description = "리뷰 내용", example = "도움이 많이 되는 강의입니다.", required = true)
    val content: String,

    @field:Schema(description = "리뷰 내용", example = "도움이 많이 되는 강의입니다.", required = true)
    @field:Min(1, message = "점수는 최소 1점이어야 합니다.")
    @field:Max(5, message = "점수는 5점을 넘을 수 없습니다.")
    val score: Int
)