package org.sam.lms.account.infrastructure.persistence.entity

import jakarta.persistence.*
import org.sam.lms.account.domain.Account
import org.sam.lms.infra.persistence.AuditEntity

@Table(name = "account")
@Entity
class AccountJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column
    val email: String,

    @Column
    val name: String,

    @Column
    val password: String,

    @OneToMany
    val accountRoles: Set<AccountRoleJpaEntity> = setOf(),
) : AuditEntity() {
    fun toDomain(): Account {
        return Account(
            id = this.id,
            email = this.email,
            name = this.name,
            role = this.accountRoles.first().roleJpaEntity.name,
            password = this.password
        )
    }
}