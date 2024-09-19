package org.sam.gateway.security

import org.sam.gateway.account.AccountRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class CustomUserDetailsService(private val accountRepository: AccountRepository) : ReactiveUserDetailsService {
    override fun findByUsername(email: String): Mono<UserDetails> {
        return accountRepository.findAccountWithRoleByEmail(email)
            .map { accountWithRole -> CustomUserDetails(accountWithRole) }
    }
}