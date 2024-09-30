package org.sam.lms.store.domain.order.domain

interface OrderRepository {
    fun findByOrderNo(orderNo: String): Order?
    fun save(order: Order): Order
}