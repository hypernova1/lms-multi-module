package org.sam.lms.account.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AccountRoleId(
    @Column(name = "account_id")
    val accountId: Long = 0,
    @Column(name = "role_id")
    val roleId: Long = 0,
) : Serializable