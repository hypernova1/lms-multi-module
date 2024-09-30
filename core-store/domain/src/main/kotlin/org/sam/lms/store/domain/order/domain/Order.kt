package org.sam.lms.store.domain.order.domain

import jakarta.persistence.*
import org.sam.lms.store.domain.order.application.`in`.CreateOrderDto
import org.sam.lms.store.domain.persistence.AuditEntity

@Table(name = "orders")
@Entity
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderNo: String
) : AuditEntity() {

    companion object {
        fun create(createOrderDto: CreateOrderDto): Order {
            return Order(
                orderNo = OrderNoCreator.create()
            )
        }
    }

}