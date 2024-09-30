package org.sam.lms.store.domain.payment.domain

import jakarta.persistence.*
import org.sam.lms.common.exception.BadRequestException
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.store.domain.payment.application.`in`.CreatePaymentDto
import org.sam.lms.store.domain.payment.application.`in`.PaymentFailureDto
import org.sam.lms.store.domain.payment.application.`in`.PaymentSuccessDto
import org.sam.lms.store.domain.persistence.AuditEntity
import org.sam.lms.store.domain.provider.Provider

@Table(
    name = "payment", indexes = [
        Index(name = "payment_order_no_idx", columnList = "order_no"),
        Index(name = "payment_account_id_idx", columnList = "account_id"),
    ]
)
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
    var status: PaymentStatus,

    @Column(name = "paidPrice", columnDefinition = "integer", nullable = false)
    val paidPrice: Int,

    @Column(name = "paidTime", columnDefinition = "integer", nullable = true)
    var paidTime: Int? = null,

    @Column(name = "payment_key", columnDefinition = "varchar", nullable = true)
    var paymentKey: String? = null,

    @Column(name = "order_no", columnDefinition = "varchar", nullable = true)
    val orderNo: String,

    @Column(name = "account_id", columnDefinition = "bigint", nullable = true)
    val accountId: Long,

    @OneToMany(mappedBy = "payment", cascade = [CascadeType.ALL])
    val logs: MutableList<PaymentLog> = mutableListOf()

) : AuditEntity() {

    fun complete(paymentSuccessDto: PaymentSuccessDto) {
        if (this.status != PaymentStatus.WAITING) {
            throw BadRequestException(ErrorCode.NOT_WAITING_PAYMENT)
        }

        this.status = PaymentStatus.PAID
        this.paymentKey = paymentSuccessDto.paymentKey
        this.logs.add(PaymentLog(status = PaymentStatus.PAID, resultCode = paymentSuccessDto.resultCode, payment = this))
    }

    fun failure(paymentFailureDto: PaymentFailureDto) {
        if (this.status == PaymentStatus.WAITING) {
            throw BadRequestException(ErrorCode.ALREADY_PAID)
        }

        this.status = PaymentStatus.FAILED
        this.logs.add(
            PaymentLog(
                status = PaymentStatus.FAILED,
                resultCode = paymentFailureDto.errorCode,
                resultMessage = paymentFailureDto.errorMessage,
                payment = this
            )
        )
    }

    companion object {
        fun from(createPaymentDto: CreatePaymentDto, provider: Provider): Payment {
            val payment = Payment(
                orderNo = createPaymentDto.orderNo,
                type = createPaymentDto.type,
                status = PaymentStatus.WAITING,
                paidPrice = createPaymentDto.paidPrice,
                accountId = provider.id
            )
            payment.logs.add(PaymentLog(status = PaymentStatus.WAITING, payment = payment))
            return payment
        }
    }

}