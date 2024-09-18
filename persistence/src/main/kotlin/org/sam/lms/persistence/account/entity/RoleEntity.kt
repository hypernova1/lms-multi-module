package org.sam.lms.persistence.account.entity

import jakarta.persistence.*
import org.sam.lms.domain.account.domain.Role
import org.sam.lms.domain.account.domain.RoleName
import org.sam.lms.persistence.AuditEntity

@Table(name = "role")
@Entity
class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,
) : AuditEntity() {
    fun toDomain(): Role {
        return Role(name = RoleName.from(this.name))
    }
}