package org.sam.lms.domain.course.application

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CourseKafkaConsumer(private val courseService: CourseService) {

    @KafkaListener(topics = ["increase-enrollments-topic"], groupId = "group_1")
    fun consume(courseIds: List<Long>) {

    }

}