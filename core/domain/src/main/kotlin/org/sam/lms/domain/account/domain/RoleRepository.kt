package org.sam.lms.domain.account.domain

interface RoleRepository {
    fun findByName(name: RoleName): Role?
}