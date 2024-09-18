package org.sam.lms.domain.account.application

import org.sam.lms.common.encrypt.PasswordEncoder
import org.sam.lms.common.exception.ConflictException
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.domain.account.application.payload.`in`.AccountJoinRequest
import org.sam.lms.domain.account.application.payload.out.AccountSummary
import org.sam.lms.domain.account.domain.Account
import org.sam.lms.domain.account.domain.AccountReader
import org.sam.lms.domain.account.domain.AccountWriter
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountReader: AccountReader,
    private val accountWriter: AccountWriter,
    private val passwordEncoder: PasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun create(accountJoinRequest: AccountJoinRequest): AccountSummary {
        val existsAccount = this.accountReader.existsByEmail(accountJoinRequest.email)
        if (existsAccount) {
            throw ConflictException(ErrorCode.ALREADY_EXISTED_USER_ID)
        }

        val account = Account.of(accountJoinRequest, passwordEncoder)
        val savedAccount = this.accountWriter.save(account)
        return AccountSummary(
            id = savedAccount.id,
            email = account.email,
            name = account.name
        )
    }

    fun findOne(email: String): Account? {
        return this.accountReader.findByEmail(email)
    }

    fun delete(account: Account) {
        this.accountWriter.delete(account.id)
        if (account.isStudent()) {
            applicationEventPublisher.publishEvent(AccountDeleteEvent(account.id))
        }
    }
}