package org.sam.lms.account.domain

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException

enum class RoleName {
    TEACHER, STUDENT, ADMIN;

    fun toEntity(): String {
        return "ROLE_$this"
    }

    companion object {
        fun from(name: String): RoleName {
            return try {
                valueOf(name.removePrefix("ROLE_"))
            } catch (e: IllegalArgumentException) {
                throw NotFoundException(ErrorCode.CANNOT_FIND_ROLE_NAME)
            }
        }
    }
}