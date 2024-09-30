package org.sam.lms.store.domain.payment.application

import org.junit.jupiter.api.Test
import org.sam.lms.store.domain.TestConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
@SpringBootTest
@ActiveProfiles("infrastructure", "common")
class PaymentServiceTest {

    @Test
    fun test() {

    }

}