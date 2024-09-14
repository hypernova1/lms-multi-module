package org.sam.lms.account.domain

import org.sam.lms.account.infrastructure.persistence.entity.AccountEntity
import org.sam.lms.account.infrastructure.persistence.entity.AccountRoleEntity
import org.sam.lms.account.infrastructure.persistence.entity.RoleEntity
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.springframework.stereotype.Component

@Component
class AccountProcessor(private val accountRepository: AccountRepository, private val roleRepository: RoleRepository) {
    fun save(account: Account): Account {
        val roleEntity = this.roleRepository.findByName("ROLE_" + account.role.toString())
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FIND_ROLE_NAME) }
        val accountEntity = toEntity(account)
        val savedEntity = this.accountRepository.save(accountEntity)
        accountEntity.accountRoles.add(
            AccountRoleEntity(
                accountEntity = accountEntity, roleEntity = roleEntity,
            )
        )
        account.id = savedEntity.id
        return account
    }

    private fun toEntity(account: Account): AccountEntity {
        return AccountEntity.from(account)
    }
}