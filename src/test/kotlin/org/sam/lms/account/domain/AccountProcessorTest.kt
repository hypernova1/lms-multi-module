package org.sam.lms.account.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountProcessorTest(
    @Autowired
    private val accountProcessor: AccountProcessor) {

    @Test
    @DisplayName("유저 저장")
    fun create_user_entity() {
        val account = accountProcessor.save(
            Account(
                email = "hypemova@gmail.com",
                password = "1111",
                name = "sam",
                role = RoleName.STUDENT
            )
        )

        assertThat(account.id).isNotNull()
    }

}