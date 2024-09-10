package org.sam.lms.account.domain

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountReaderTest(
    @Autowired
    private var reader: AccountReader) {

    @Test
    fun findOne() {
        reader.findByEmail("hello");
    }

}