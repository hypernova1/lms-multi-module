package org.sam.lms.account.domain

import org.sam.lms.account.application.payload.`in`.AccountJoinRequest
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

data class Account(
    val id: Long = 0L,
    val email: String,
    var name: String,
    val role: RoleName,
    var password: String,
) {

    val createdDate: LocalDateTime = LocalDateTime.now()

    fun matchPassword(password: String, passwordEncoder: PasswordEncoder) {
        val matched = passwordEncoder.matches(password, this.password)
        if (!matched) {
            throw UnauthorizedException(ErrorCode.ACCOUNT_NOT_FOUND)
        }
    }

    companion object {
        fun of(accountJoinRequest: AccountJoinRequest, passwordEncoder: PasswordEncoder): Account {
            return Account(
                email = accountJoinRequest.email,
                name = accountJoinRequest.name,
                password = passwordEncoder.encode(accountJoinRequest.password),
                role = accountJoinRequest.type
            )
        }
    }
}