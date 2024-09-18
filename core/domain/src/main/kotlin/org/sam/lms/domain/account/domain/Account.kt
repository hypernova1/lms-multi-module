package org.sam.lms.domain.account.domain

import org.sam.lms.common.encrypt.PasswordEncoder
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.sam.lms.domain.account.application.payload.`in`.AccountJoinRequest
import java.time.LocalDateTime

data class Account(
    val id: Long = 0L,
    val email: String,
    var name: String,
    val role: Role,
    var password: String,
) {

    val createdDate: LocalDateTime = LocalDateTime.now()

    fun matchPassword(password: String, passwordEncoder: PasswordEncoder) {
        val matched = passwordEncoder.matches(password, this.password)
        if (!matched) {
            throw UnauthorizedException(ErrorCode.ACCOUNT_NOT_FOUND)
        }
    }

    fun isStudent(): Boolean {
        return this.role.name === RoleName.STUDENT
    }

    companion object {
        fun of(accountJoinRequest: AccountJoinRequest, passwordEncoder: PasswordEncoder): Account {
            return Account(
                email = accountJoinRequest.email,
                name = accountJoinRequest.name,
                password = passwordEncoder.encrypt(accountJoinRequest.password),
                role = Role(name = accountJoinRequest.role)
            )
        }
    }
}