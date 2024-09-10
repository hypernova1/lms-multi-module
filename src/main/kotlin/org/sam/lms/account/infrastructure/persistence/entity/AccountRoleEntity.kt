package org.sam.lms.account.infrastructure.persistence.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table(name = "account_roles")
@Entity
class AccountRoleEntity(

    @EmbeddedId
    val id: AccountRoleId,

    @ManyToOne
    val accountEntity: AccountEntity,

    @ManyToOne
    val roleEntity: RoleEntity)