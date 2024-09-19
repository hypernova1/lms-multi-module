package org.sam.lms.domain.account.domain

class Provider(val id: Long, val role: RoleName) {
    fun isStudent(): Boolean {
        return this.role == RoleName.STUDENT
    }

    override fun toString(): String {
        return "id=$id, role=$role"
    }
}