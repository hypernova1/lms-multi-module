package org.sam.lms.domain.account.application.payload.`in`

import org.sam.lms.domain.account.domain.RoleName

class CreateAccountDto(val email: String, val name: String, val password: String, val role: RoleName)