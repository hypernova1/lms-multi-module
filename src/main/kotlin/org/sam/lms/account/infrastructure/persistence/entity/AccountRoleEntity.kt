package org.sam.lms.account.infrastructure.persistence.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table

@Table(name = "account_roles")
@Entity
class AccountRoleEntity(

    @EmbeddedId
    val id: AccountRoleId = AccountRoleId(),

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    val accountEntity: AccountEntity,

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    val roleEntity: RoleEntity)