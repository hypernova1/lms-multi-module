package org.sam.lms.api.security

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.UnauthorizedException
import org.sam.lms.domain.account.domain.AccountReader
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