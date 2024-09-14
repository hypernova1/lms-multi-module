package org.sam.lms.auth.ui

import org.sam.lms.auth.application.AuthService
import org.sam.lms.auth.application.payload.`in`.LoginRequest
import org.sam.lms.auth.application.payload.out.TokenDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<TokenDto> {
        val tokenDto = this.authService.login(loginRequest)
        return ResponseEntity.ok(tokenDto)
    }

}