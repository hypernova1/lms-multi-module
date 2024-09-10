package org.sam.lms.account.infrastructure.persistence.repository

import org.sam.lms.account.domain.AccountRepository
import org.sam.lms.account.infrastructure.persistence.entity.AccountJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface AccountJpaRepository : JpaRepository<AccountJpaEntity, Long>, AccountRepository {

    @Query("SELECT account, role " +
            "FROM AccountJpaEntity account " +
            "JOIN FETCH account.accountRoles accountRole " +
            "JOIN FETCH accountRole.roleJpaEntity role " +
            "WHERE account.email = :email")
    override fun findByEmail(email: String): Optional<AccountJpaEntity>

}