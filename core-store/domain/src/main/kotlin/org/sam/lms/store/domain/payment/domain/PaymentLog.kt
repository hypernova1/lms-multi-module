package org.sam.lms.store.domain.payment.domain

import jakarta.persistence.*
import jakarta.persistence.ForeignKey
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.*
import org.sam.lms.store.domain.persistence.AuditEntity

@FilterDef(name = "deletedPaymentLogFilter", parameters = [ParamDef(name = "deletedDate", type = Boolean::class)])
@Filter(name = "deletedPaymentLogFilter", condition = "deleted_date IS NULL")
@SQLDelete(sql = "UPDATE payment_log SET deleted_date = current_timestamp WHERE id = ?")
@SQLRestriction("deleted_date is null")
@Table(name = "payment_log", indexes = [Index(name = "payment_log_payment_id_idx", columnList = "payment_id")])
@Entity
class PaymentLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "status", columnDefinition = "varchar", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: PaymentStatus,

    @Column(name = "result_code", columnDefinition = "varchar", nullable = true)
    var resultCode: String? = "",

    @Column(name = "result_message", columnDefinition = "varchar", nullable = true)
    var resultMessage: String? = "",

    @ManyToOne
    @JoinColumn(name = "payment_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val payment: Payment
) : AuditEntity()