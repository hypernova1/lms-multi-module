package org.sam.lms.account.domain

import org.sam.lms.account.infrastructure.persistence.entity.RoleEntity
import java.util.*

interface RoleRepository {
    fun findByName(name: String): Optional<RoleEntity>
}