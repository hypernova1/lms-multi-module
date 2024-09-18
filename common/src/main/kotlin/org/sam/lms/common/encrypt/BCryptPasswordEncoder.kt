package org.sam.lms.common.encrypt

import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class BCryptPasswordEncoder : PasswordEncoder {
    override fun encrypt(rawPassword: String): String {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt())
    }

    override fun matches(rawPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(rawPassword, hashedPassword)
    }
}