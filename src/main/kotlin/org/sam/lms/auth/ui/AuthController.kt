package org.sam.lms.auth.ui

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.sam.lms.auth.application.AuthService
import org.sam.lms.auth.application.payload.`in`.LoginRequest
import org.sam.lms.auth.application.payload.out.TokenDto
import org.sam.lms.infra.swagger.annotation.SwaggerOkResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @Operation(summary = "로그인")
    @SwaggerOkResponse(summary = "로그인 성공", content = [Content(schema = Schema(implementation = TokenDto::class))])
    @PostMapping
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<TokenDto> {
        val tokenDto = this.authService.login(loginRequest)
        return ResponseEntity.ok(tokenDto)
    }

}