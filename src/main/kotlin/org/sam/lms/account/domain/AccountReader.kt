package org.sam.lms.account.domain

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.springframework.stereotype.Component

@Component
class AccountReader(private val accountRepository: AccountRepository) {

    fun findByEmail(email: String): Account {
        val accountEntity = this.accountRepository.findByEmail(email)
            .orElseThrow { NotFoundException(ErrorCode.ACCOUNT_NOT_FOUND) }

        return accountEntity.toDomain();
    }

    fun existsByEmail(email: String): Boolean {
        return this.accountRepository.existsByEmail(email)
    }

}