package org.sam.lms.account.domain

import java.time.LocalDateTime

class Account(
    val id: Long,
    val email: String,
    var name: String,
    val role: RoleName,
    var password: String,
    val createdDate: LocalDateTime = LocalDateTime.now()
) {
}