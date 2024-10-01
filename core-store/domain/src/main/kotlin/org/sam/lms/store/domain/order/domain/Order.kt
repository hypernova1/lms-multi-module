package org.sam.lms.store.domain.order.domain

import jakarta.persistence.*
import jakarta.persistence.CascadeType
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.*
import org.sam.lms.store.domain.cart.domain.Cart
import org.sam.lms.store.domain.order.application.`in`.CreateOrderDto
import org.sam.lms.store.domain.persistence.AuditEntity
import org.sam.lms.store.domain.provider.Provider
import java.time.LocalDateTime

@FilterDef(name = "deletedOrderFilter", parameters = [ParamDef(name = "deletedDate", type = Boolean::class)])
@Filter(name = "deletedOrderFilter", condition = "deleted_date IS NULL")
@SQLDelete(sql = "UPDATE orders SET deleted_date = current_timestamp WHERE order_no = ?")
@SQLRestriction("deleted_date is null")
@Table(name = "orders", indexes = [Index(name = "order_account_id_idx", columnList = "account_id")])
@Entity
class Order(
    @Id
    val orderNo: String,

    var status: OrderStatus = OrderStatus.BEFORE_PAYMENT,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderLines: MutableList<OrderLine> = mutableListOf(),

    @Column(name = "account_id", columnDefinition = "bigint", nullable = true)
    val accountId: Long,

    ) : AuditEntity() {
    val isPaymentEligible: Boolean
        get() = this.createdDate.plusMinutes(5)
            .isBefore(LocalDateTime.now()) || this.status != OrderStatus.BEFORE_PAYMENT

    companion object {
        fun create(items: List<OrderItemDto>, provider: Provider): Order {
            val order = Order(
                orderNo = OrderNoCreator.create(),
                accountId = provider.id,
            )

            for (orderItem in items) {
                order.orderLines.add(OrderLine(courseId = orderItem.courseId, price = orderItem.price, order = order))
            }

            return order
        }
    }

}