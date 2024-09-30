package org.sam.lms.store.domain.order.domain

import jakarta.persistence.*
import org.sam.lms.store.domain.order.application.`in`.CreateOrderDto
import org.sam.lms.store.domain.persistence.AuditEntity
import java.time.LocalDateTime

@Table(name = "orders")
@Entity
class Order(
    @Id
    val orderNo: String,

    var status: OrderStatus = OrderStatus.BEFORE_PAYMENT,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderLines: MutableList<OrderLine> = mutableListOf()

) : AuditEntity() {
    val isPaymentEligible: Boolean
        get() = this.createdDate.plusMinutes(5).isBefore(LocalDateTime.now()) || this.status != OrderStatus.BEFORE_PAYMENT

    companion object {
        fun create(createOrderDto: CreateOrderDto): Order {
            val order = Order(
                orderNo = OrderNoCreator.create()
            )
            order.orderLines.add(OrderLine(courseId = createOrderDto.courseId, order = order))
            return order
        }
    }

}