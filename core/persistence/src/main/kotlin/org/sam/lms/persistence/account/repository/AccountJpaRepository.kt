package org.sam.lms.persistence.account.repository

import org.sam.lms.persistence.account.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountJpaRepository : JpaRepository<AccountEntity, Long> {

    fun findByEmail(email: String): Optional<AccountEntity>
    fun existsByEmail(email: String): Boolean

}