package org.sam.lms.account.infrastructure.persistence.entity

import jakarta.persistence.*
import org.sam.lms.account.domain.Account
import org.sam.lms.account.domain.RoleName
import org.sam.lms.infra.persistence.AuditEntity

@Table(name = "account")
@Entity
class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false, columnDefinition = "varchar")
    val email: String,

    @Column(nullable = false, columnDefinition = "varchar")
    val name: String,

    @Column(nullable = false, columnDefinition = "varchar")
    val password: String,

    @OneToMany(mappedBy = "accountEntity", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val accountRoles: MutableSet<AccountRoleEntity> = mutableSetOf(),
) : AuditEntity() {
    fun toDomain(): Account {
        return Account(
            id = this.id,
            email = this.email,
            name = this.name,
            role = RoleName.fromEntityName(this.accountRoles.first().roleEntity.name),
            password = this.password
        )
    }

    companion object {
        fun from(account: Account): AccountEntity {
            return AccountEntity(
                name = account.name, email = account.email, password = account.password
            )
        }
    }
}