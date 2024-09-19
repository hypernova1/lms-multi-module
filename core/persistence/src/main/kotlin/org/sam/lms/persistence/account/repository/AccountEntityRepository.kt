package org.sam.lms.persistence.account.repository

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.domain.account.domain.Account
import org.sam.lms.domain.account.domain.AccountRepository
import org.sam.lms.persistence.account.entity.AccountEntity
import org.springframework.stereotype.Repository

@Repository
class AccountEntityRepository(
    private val accountJpaRepository: AccountJpaRepository,
    private val roleJpaRepository: RoleJpaRepository
) : AccountRepository {
    override fun findByEmail(email: String): Account? {
        val accountEntity = this.accountJpaRepository.findByEmail(email)
            .orElse(null)
        val roleEntity = this.roleJpaRepository.findById(accountEntity.roleId)
            .orElseThrow { NotFoundException(ErrorCode.ROLE_NAME_NOT_FOUND) }
        return accountEntity?.toDomain(roleEntity);
    }

    override fun existsByEmail(email: String): Boolean {
        return this.accountJpaRepository.existsByEmail(email)
    }

    override fun save(account: Account): Account {
        val roleEntity = this.roleJpaRepository.findByName("ROLE_" + account.role.name) ?: throw NotFoundException(
            ErrorCode.ROLE_NAME_NOT_FOUND
        )
        val accountEntity = toEntity(account, roleEntity.id)
        val savedEntity = this.accountJpaRepository.save(accountEntity)
        return account.copy(id = savedEntity.id)
    }

    private fun toEntity(account: Account, roleId: Long): AccountEntity {
        return AccountEntity.from(account, roleId)
    }

    override fun deleteById(id: Long) {
        this.accountJpaRepository.deleteById(id)
    }
}