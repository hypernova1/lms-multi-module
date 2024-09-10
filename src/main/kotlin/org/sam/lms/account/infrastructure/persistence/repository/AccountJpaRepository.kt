package org.sam.lms.account.infrastructure.persistence.repository

import org.sam.lms.account.domain.AccountRepository
import org.sam.lms.account.infrastructure.persistence.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface AccountJpaRepository : AccountRepository, JpaRepository<AccountEntity, Long> {

    @Query("SELECT account, role " +
            "FROM AccountEntity account " +
            "JOIN FETCH account.accountRoles accountRole " +
            "JOIN FETCH accountRole.roleEntity role " +
            "WHERE account.email = :email")
    override fun findByEmail(email: String): Optional<AccountEntity>

}