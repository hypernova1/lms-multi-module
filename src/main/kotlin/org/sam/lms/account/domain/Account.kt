package org.sam.lms.account.domain

class Account(val id: Long, val email: String, val name: String, val role: RoleName, val password: String) {
}