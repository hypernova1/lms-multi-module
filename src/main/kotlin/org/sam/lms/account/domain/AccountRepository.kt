package org.sam.lms.account.domain

import org.sam.lms.account.infrastructure.persistence.entity.AccountJpaEntity
import java.util.Optional

interface AccountRepository {
    fun findByEmail(email: String): Optional<AccountJpaEntity>
}