package org.sam.lms.domain.account.domain

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException


enum class RoleName {
    TEACHER, STUDENT, ADMIN;

    companion object {
        fun from(name: String): RoleName {
            return entries.find { it.name == name } ?: throw NotFoundException(ErrorCode.ROLE_NAME_NOT_FOUND)
        }
    }
}