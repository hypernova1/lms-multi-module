package org.sam.lms.account.application.payload.`in`

import org.sam.lms.account.domain.RoleName

class AccountJoinRequest(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val type: RoleName = RoleName.STUDENT
)