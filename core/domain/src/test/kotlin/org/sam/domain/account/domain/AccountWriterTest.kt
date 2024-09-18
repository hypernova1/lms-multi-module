package org.sam.domain.account.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountWriterTest(
    @Autowired
    private val accountWriter: AccountWriter) {

    @Test
    @DisplayName("유저 저장")
    fun create_user_entity() {
        val account = accountWriter.save(
            Account(
                email = "hypemova@gmail.com",
                password = "1111",
                name = "sam",
                role = Role(name = org.sam.lms.domain.account.domain.RoleName.STUDENT)
            )
        )

        assertThat(account.id).isNotNull()
    }

}