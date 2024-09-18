package org.sam.lms.domain.account.domain

import org.springframework.stereotype.Component

@Component
class AccountWriter(private val accountRepository: AccountRepository) {
    fun save(account: Account): Account {
        val savedAccount = this.accountRepository.save(account)
        return savedAccount
    }

    fun delete(id: Long) {
        accountRepository.deleteById(id)
    }
}