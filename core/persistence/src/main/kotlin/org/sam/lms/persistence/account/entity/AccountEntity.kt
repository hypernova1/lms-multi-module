package org.sam.lms.persistence.account.entity

import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*
import org.sam.lms.domain.account.domain.Account
import org.sam.lms.domain.account.domain.Role
import org.sam.lms.domain.account.domain.RoleName
import org.sam.lms.persistence.AuditEntity

@FilterDef(name = "deletedAccountFilter", parameters = [ParamDef(name = "deletedDate", type = Boolean::class)])
@Filter(name = "deletedAccountFilter", condition = "deleted_date IS NULL")
@SQLRestriction("deleted_date is null")
@SQLDelete(sql = "UPDATE account SET deleted_date = current_timestamp WHERE id = ?")
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

    @Column()
    val roleId: Long,
) : AuditEntity() {
    fun toDomain(roleEntity: RoleEntity): Account {
        return Account(
            id = this.id,
            email = this.email,
            name = this.name,
            password = this.password,
            role = Role(id = roleEntity.id, name = RoleName.from(roleEntity.name.removePrefix("ROLE_")))
        )
    }

    companion object {
        fun from(account: Account, roleId: Long): AccountEntity {
            return AccountEntity(
                name = account.name, email = account.email, password = account.password, roleId = roleId,
            )
        }
    }
}