package org.sam.lms.store.api.order

import jakarta.validation.Valid
import org.sam.lms.store.api.order.request.CreateOrderRequest
import org.sam.lms.store.api.order.response.OrderResponse
import org.sam.lms.store.domain.order.application.OrderService
import org.sam.lms.store.domain.order.application.`in`.CreateOrderDto
import org.sam.lms.store.domain.provider.Provider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/store/api/v1/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun order(@Valid @RequestBody createOrderRequest: CreateOrderRequest, provider: Provider): ResponseEntity<OrderResponse> {
        val result = this.orderService.order(CreateOrderDto(courseId = createOrderRequest.courseId), provider)
        return ResponseEntity.ok(OrderResponse(orderNo = result.orderNo, paidPrice = result.paidPrice))
    }

}