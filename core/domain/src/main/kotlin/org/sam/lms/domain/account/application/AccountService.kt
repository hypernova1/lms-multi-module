package org.sam.lms.domain.account.application

import org.sam.lms.common.encrypt.PasswordEncoder
import org.sam.lms.common.exception.ConflictException
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.domain.account.domain.Provider
import org.sam.lms.domain.account.application.payload.`in`.CreateAccountDto
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
    fun create(createAccountDto: CreateAccountDto): AccountSummary {
        val existsAccount = this.accountReader.existsByEmail(createAccountDto.email)
        if (existsAccount) {
            throw ConflictException(ErrorCode.ALREADY_EXISTED_USER_ID)
        }

        val account = Account.of(createAccountDto, passwordEncoder)
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

    fun delete(provider: Provider) {
        this.accountWriter.delete(provider.id)
        if (provider.isStudent()) {
            applicationEventPublisher.publishEvent(AccountDeleteEvent(provider.id))
        }
    }
}