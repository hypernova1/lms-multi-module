package org.sam.lms.infra.security

import org.sam.lms.account.domain.Account
import org.sam.lms.account.domain.AccountReader
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(private val accountReader: AccountReader) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val account = accountReader.findByEmail(email) ?: throw UnauthorizedException(ErrorCode.ACCOUNT_NOT_FOUND)
        return CustomUserDetails(account)
    }
}