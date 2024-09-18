package org.sam.lms.domain.auth.application.payload.`in`

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Length

class LoginRequest(
    @field:Schema(description = "이메일", example = "hypemova@gmail.com", required = true)
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    @field:NotBlank
    val email: String = "",

    @field:Schema(description = "비밀번호", example = "1111", required = true)
    @field:Size(min = 4, max = 20, message = "비밀번호는 4자리 이상, 20자리 이하여야 합니다.")
    val password: String = ""
)