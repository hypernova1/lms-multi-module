package org.sam.lms.store.domain.order.domain

import jakarta.persistence.*
import org.sam.lms.store.domain.order.application.`in`.CreateOrderDto
import org.sam.lms.store.domain.persistence.AuditEntity

@Table(name = "orders")
@Entity
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderNo: String,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderLines: MutableList<OrderLine> = mutableListOf()

) : AuditEntity() {

    companion object {
        fun create(createOrderDto: CreateOrderDto): Order {
            val order = Order(
                orderNo = OrderNoCreator.create())

            order.orderLines.add(OrderLine(courseId = createOrderDto.courseId, order = order))
            return order
        }
    }

}