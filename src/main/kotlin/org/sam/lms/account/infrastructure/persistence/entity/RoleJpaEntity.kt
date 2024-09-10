package org.sam.lms.account.infrastructure.persistence.entity

import jakarta.persistence.*
import org.sam.lms.account.domain.RoleName
import org.sam.lms.infra.persistence.AuditEntity

@Table(name = "role")
@Entity
class RoleJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val name: RoleName,
) : AuditEntity() {
}