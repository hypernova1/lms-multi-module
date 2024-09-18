package org.sam.lms.persistence.account.repository

import org.sam.lms.persistence.account.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleJpaRepository : JpaRepository<RoleEntity, Long> {
    fun findByName(toEntityName: String): RoleEntity?
}