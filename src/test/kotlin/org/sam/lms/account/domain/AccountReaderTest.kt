package org.sam.lms.account.domain

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountReaderTest(
    @Autowired
    private val reader: AccountReader,
    @Autowired
    private val accountProcessor: AccountProcessor
) {

    @Test
    fun findOne() {
        this.accountProcessor.save(
            Account(
                email = "hypemova@gmail.com",
                password = "1111",
                name = "sam",
                role = RoleName.STUDENT
            )
        )

        reader.findByEmail("hypemova@gmail.com");
    }

}