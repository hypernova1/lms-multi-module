package org.sam.lms.domain.account.domain

import org.springframework.stereotype.Component

@Component
class AccountReader(private val accountRepository: AccountRepository) {

    fun findByEmail(email: String): Account? {
        return accountRepository.findByEmail(email)
    }

    fun existsByEmail(email: String): Boolean {
        return this.accountRepository.existsByEmail(email)
    }

}