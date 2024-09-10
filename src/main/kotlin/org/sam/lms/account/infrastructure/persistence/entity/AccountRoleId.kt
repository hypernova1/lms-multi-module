package org.sam.lms.account.infrastructure.persistence.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class AccountRoleId(
    val accountId: Long,
    val roleId: Long,
) : Serializable