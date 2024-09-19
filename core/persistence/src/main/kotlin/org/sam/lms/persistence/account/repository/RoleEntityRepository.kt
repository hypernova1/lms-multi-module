package org.sam.lms.persistence.account.repository

import org.sam.lms.domain.account.domain.Role
import org.sam.lms.domain.account.domain.RoleName
import org.sam.lms.domain.account.domain.RoleRepository
import org.springframework.stereotype.Repository

@Repository
class RoleEntityRepository(private val roleJpaRepository: RoleJpaRepository) : RoleRepository {
    override fun findByName(name: RoleName): Role? {
        return this.roleJpaRepository.findByName("ROLE_$name")?.toDomain()
    }
}