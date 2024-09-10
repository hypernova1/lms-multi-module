package org.sam.lms.account.infrastructure.persistence.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table(name = "account_roles")
@Entity
class AccountRoleJpaEntity(

    @EmbeddedId
    val id: AccountRoleId,

    @ManyToOne
    val accountJpaEntity: AccountJpaEntity,

    @ManyToOne
    val roleJpaEntity: RoleJpaEntity)