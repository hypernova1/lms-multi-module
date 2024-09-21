package org.sam.lms.domain.auth.application

import org.sam.lms.common.encrypt.PasswordEncoder
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.sam.lms.common.util.JwtTokenProvider
import org.sam.lms.domain.account.application.AccountService
import org.sam.lms.domain.auth.application.payload.`in`.LoginDto
import org.sam.lms.domain.auth.application.payload.out.TokenDto
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val accountService: AccountService,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: JwtTokenProvider
) {

    fun login(loginRequest: LoginDto): TokenDto {
        val account = this.accountService.findOne(loginRequest.email)
            ?: throw UnauthorizedException(ErrorCode.ACCOUNT_NOT_FOUND)

        account.matchPassword(loginRequest.password, passwordEncoder)

        val accessToken = this.tokenProvider.generateAccessToken(account.email)
        val refreshToken = this.tokenProvider.generateRefreshToken(account.email)

        return TokenDto(accessToken, refreshToken)
    }

}