package org.sam.lms.account.domain

import org.springframework.stereotype.Component

@Component
class AccountReader(private val accountRepository: AccountRepository) {

    fun findByEmail(email: String): Account? {
        val accountEntity = this.accountRepository.findByEmail(email)
            .orElse(null)

        return accountEntity?.toDomain();
    }

    fun existsByEmail(email: String): Boolean {
        return this.accountRepository.existsByEmail(email)
    }

}