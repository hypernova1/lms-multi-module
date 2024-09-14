package org.sam.lms.account.application

import org.sam.lms.account.application.payload.`in`.AccountJoinRequest
import org.sam.lms.account.application.payload.out.AccountSummary
import org.sam.lms.account.domain.Account
import org.sam.lms.account.domain.AccountProcessor
import org.sam.lms.account.domain.AccountReader
import org.sam.lms.common.exception.ConflictException
import org.sam.lms.common.exception.ErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountReader: AccountReader,
    private val accountProcessor: AccountProcessor,
    private val passwordEncoder: PasswordEncoder
) {
    fun create(accountJoinRequest: AccountJoinRequest): AccountSummary {
        val existsAccount = this.accountReader.existsByEmail(accountJoinRequest.email)
        if (existsAccount) {
            throw ConflictException(ErrorCode.ALREADY_EXISTED_USER_ID)
        }

        val account = Account.of(accountJoinRequest, passwordEncoder)
        this.accountProcessor.save(account)
        return AccountSummary(id = account.id, email = account.email, name = account.name)
    }

    fun findOne(email: String): Account? {
        return this.accountReader.findByEmail(email)
    }
}