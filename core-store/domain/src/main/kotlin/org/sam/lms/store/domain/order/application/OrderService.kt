package org.sam.lms.store.domain.order.application

import org.sam.lms.client.course.CourseClient
import org.sam.lms.common.exception.BadRequestException
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.store.domain.order.application.`in`.CreateOrderDto
import org.sam.lms.store.domain.order.application.out.CreateOrderResultDto
import org.sam.lms.store.domain.order.domain.Order
import org.sam.lms.store.domain.order.domain.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val courseClient: CourseClient
) {
    fun order(createOrderDto: CreateOrderDto): CreateOrderResultDto {
        val courseDetail = courseClient.getCourseDetail(createOrderDto.courseId)
        if (!courseDetail.available) {
            throw BadRequestException(ErrorCode.COURSE_FULL)
        }

        val order = Order.create(createOrderDto)
        this.orderRepository.save(order)
        return CreateOrderResultDto(
            orderNo = order.orderNo,
            paidPrice = courseDetail.price,
        )
    }

    fun findOne(orderNo: String): Order? {
        return this.orderRepository.findByOrderNo(orderNo)
    }

}