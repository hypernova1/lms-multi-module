package org.sam.lms.auth.application

import org.sam.lms.account.application.AccountService
import org.sam.lms.auth.application.payload.`in`.LoginRequest
import org.sam.lms.auth.application.payload.out.TokenDto
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.sam.lms.infra.security.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(private val accountService: AccountService, val passwordEncoder: PasswordEncoder, private val tokenProvider: JwtTokenProvider) {

    fun login(loginRequest: LoginRequest): TokenDto {
        val account = this.accountService.findOne(loginRequest.email)
            ?: throw UnauthorizedException(ErrorCode.ACCOUNT_NOT_FOUND)

        account.matchPassword(loginRequest.password, passwordEncoder)

        val accessToken = this.tokenProvider.generateAccessToken(account.email)
        val refreshToken = this.tokenProvider.generateRefreshToken(account.email)

        return TokenDto(accessToken, refreshToken)
    }

}