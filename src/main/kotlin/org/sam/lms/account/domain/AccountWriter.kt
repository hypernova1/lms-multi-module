package org.sam.lms.account.domain

import org.sam.lms.account.infrastructure.persistence.entity.AccountEntity
import org.sam.lms.account.infrastructure.persistence.entity.AccountRoleEntity
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.springframework.stereotype.Component

@Component
class AccountWriter(private val accountRepository: AccountRepository, private val roleRepository: RoleRepository) {
    fun save(account: Account): Account {
        val roleEntity = this.roleRepository.findByName(account.role.toEntityName())
            .orElseThrow { NotFoundException(ErrorCode.ROLE_NAME_NOT_FOUND) }
        val accountEntity = toEntity(account)
        accountEntity.accountRoles.add(
            AccountRoleEntity(
                accountEntity = accountEntity, roleEntity = roleEntity,
            )
        )
        val savedEntity = this.accountRepository.save(accountEntity)
        return account.copy(id = savedEntity.id)
    }

    private fun toEntity(account: Account): AccountEntity {
        return AccountEntity.from(account)
    }

    fun delete(id: Long) {
        accountRepository.deleteById(id)
    }
}