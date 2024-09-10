package org.sam.lms.infra.security

import org.sam.lms.account.domain.Account
import org.sam.lms.account.domain.AccountReader
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(private val accountReader: AccountReader) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val account: Account = accountReader.findByEmail(email)
        return CustomUserDetails(account)
    }
}