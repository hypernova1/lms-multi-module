package org.sam.lms.store.domain.order

import org.sam.lms.client.course.CourseClient
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository, val courseClient: CourseClient) {
    fun order(orderRequest: OrderRequest): String {
        courseClient.getCourseDetail(orderRequest.courseId)
        return "hello"
    }

}