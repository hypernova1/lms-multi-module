package org.sam.lms.account.infrastructure.persistence.repository

import org.sam.lms.account.domain.RoleRepository
import org.sam.lms.account.infrastructure.persistence.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleJpaRepository : JpaRepository<RoleEntity, Long>, RoleRepository {
}