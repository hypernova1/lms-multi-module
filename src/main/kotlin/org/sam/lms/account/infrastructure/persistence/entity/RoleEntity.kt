package org.sam.lms.account.infrastructure.persistence.entity

import jakarta.persistence.*
import org.sam.lms.account.domain.RoleName
import org.sam.lms.infra.persistence.AuditEntity

@Table(name = "role")
@Entity
class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,
) : AuditEntity() {
}