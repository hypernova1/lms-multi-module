package org.sam.lms.account.application.payload.`in`

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.sam.lms.account.domain.RoleName

class AccountJoinRequest(

    @field:Schema(description = "이메일", example = "hypemova@gmail.com", required = true)
    @field:NotBlank(message = "이메일은 필수입니다.")
    val email: String = "",

    @field:Schema(description = "이름", example = "권샘찬", required = true)
    @field:NotBlank(message = "이름은 필수입니다.")
    val name: String = "",

    @field:Schema(description = "비밀번호", example = "1111", required = true)
    @field:Size(min = 4, max = 20, message = "비밀번호는 4자리 이상, 20자리 이하여야 합니다.")
    val password: String = "",

    @field:Schema(description = "유저 타입", example = "STUDENT", allowableValues = ["TEACHER", "STUDENT"], required = true)
    @field:NotBlank(message = "유저 타입은 필수입니다.")
    val type: RoleName = RoleName.STUDENT
)