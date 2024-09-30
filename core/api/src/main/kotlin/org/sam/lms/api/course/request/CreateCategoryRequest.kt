package org.sam.lms.api.course.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class CreateCategoryRequest(
    @field:Schema(description = "카테고리명", example = "게임 개발", required = true)
    @field:NotBlank(message = "카테고리명은 필수입니다.")
    val name: String = ""
)