package org.sam.lms.infra.security

import org.sam.lms.account.domain.AccountRepository
import org.sam.lms.account.infrastructure.persistence.entity.AccountJpaEntity
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(private val accountRepository: AccountRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user: AccountJpaEntity = accountRepository.findByEmail(email)
            .orElseThrow { NotFoundException() }
        return CustomUserDetails(user.toDomain())
    }
}