package org.sam.lms.store.domain.payment.domain

import jakarta.persistence.*
import org.sam.lms.store.domain.payment.application.`in`.CreatePaymentDto
import org.sam.lms.store.domain.persistence.AuditEntity
import org.sam.lms.store.domain.provider.Provider

@Table(name =  "payment", indexes = [
    Index(name = "payment_order_no_idx", columnList = "order_no"),
    Index(name = "payment_account_id_idx", columnList = "account_id"),
])
@Entity
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "type", columnDefinition = "varchar", nullable = false)
    @Enumerated(EnumType.STRING)
    val type: PaymentType,

    @Column(name = "status", columnDefinition = "varchar", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: PaymentStatus,

    @Column(name = "paidPrice", columnDefinition = "integer", nullable = false)
    val paidPrice: Int,

    @Column(name = "paidTime", columnDefinition = "integer", nullable = true)
    val paidTime: Int? = null,

    @Column(name = "order_no", columnDefinition = "varchar", nullable = true)
    val orderNo: String,

    @Column(name = "account_id", columnDefinition = "bigint", nullable = true)
    val accountId: Long

) : AuditEntity() {

    companion object {
        fun from(createPaymentDto: CreatePaymentDto, provider: Provider): Payment {
            return Payment(
                orderNo =  createPaymentDto.orderNo,
                type = createPaymentDto.type,
                status = PaymentStatus.WAITING,
                paidPrice = createPaymentDto.paidPrice,
                accountId = provider.id
            )
        }
    }

}