package org.sam.lms.store.domain.order.application

import org.sam.lms.client.course.CourseClient
import org.sam.lms.store.domain.order.application.`in`.CreateOrderDto
import org.sam.lms.store.domain.order.domain.Order
import org.sam.lms.store.domain.order.domain.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository, val courseClient: CourseClient) {
    fun order(orderRequest: CreateOrderDto): String {
        courseClient.getCourseDetail(orderRequest.courseId)
        return "hello"
    }

    fun findOne(orderNo: String): Order? {
        return this.orderRepository.findByOrderNo(orderNo)
    }

}