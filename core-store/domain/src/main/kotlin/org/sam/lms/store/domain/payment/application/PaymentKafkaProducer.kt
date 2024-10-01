package org.sam.lms.store.domain.payment.application

import org.sam.lms.store.domain.order.application.OrderService
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class PaymentKafkaProducer(private val kafkaTemplate: KafkaTemplate<String, Any>, private val orderService: OrderService) {

    private val TOPIC = "increase-enrollments-topic"

    fun paymentFailure(orderNo: String) {
        val order = this.orderService.findOne(orderNo)
            ?:
            //TODO: 예외처리
            return

        println( order.orderLines.map { it.courseId })
        this.kafkaTemplate.send(TOPIC, order.orderLines.map { it.courseId })
    }

}