package org.sam.lms.common.encrypt

interface PasswordEncoder {

    fun encrypt(rawPassword: String): String

    fun matches(rawPassword: String, hashedPassword: String): Boolean

}