package org.sam.lms.store.api

import org.sam.lms.store.domain.order.OrderRequest
import org.sam.lms.store.domain.order.OrderResponse
import org.sam.lms.store.domain.order.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/store/api/v1/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun order(@RequestBody orderRequest: OrderRequest): ResponseEntity<OrderResponse> {
        val orderNo = this.orderService.order(orderRequest)
        return ResponseEntity.ok(OrderResponse(orderNo = orderNo))
    }

}