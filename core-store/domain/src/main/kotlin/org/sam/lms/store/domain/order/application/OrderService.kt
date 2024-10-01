package org.sam.lms.store.domain.order.application

import org.sam.lms.client.course.CourseClient
import org.sam.lms.common.exception.BadRequestException
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.store.domain.cart.application.CartService
import org.sam.lms.store.domain.order.application.out.CreateOrderResultDto
import org.sam.lms.store.domain.order.domain.Order
import org.sam.lms.store.domain.order.domain.OrderItemDto
import org.sam.lms.store.domain.order.domain.OrderRepository
import org.sam.lms.store.domain.provider.Provider
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val courseClient: CourseClient,
    private val cartService: CartService
) {
    fun order(provider: Provider): CreateOrderResultDto {
        val cart = cartService.findOne(provider.id)
        if (cart.isEmpty()) {
            throw BadRequestException(ErrorCode.CART_EMPTY)
        }

        val courses = courseClient.getCourseList(cart.items.map { it.courseId })
        val hasFullSeats = courses.any {
            it.maxEnrollments <= it.numberOfStudents
        }

        if (hasFullSeats) {
            throw BadRequestException(ErrorCode.COURSE_FULL)
        }

        val orderItemDtoList = cart.items.map { cartItem ->
            OrderItemDto(
                courseId = cartItem.courseId,
                courses.find { course -> course.id == cartItem.courseId }!!.price
            )
        }

        val order = Order.create(orderItemDtoList, provider)
        this.orderRepository.save(order)
        return CreateOrderResultDto(
            orderNo = order.orderNo,
            paidPrice = courses.sumOf { it.price },
        )
    }

    fun findOne(orderNo: String): Order? {
        return this.orderRepository.findByOrderNo(orderNo)
    }

}