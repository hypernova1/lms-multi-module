package org.sam.lms.store.domain.payment.domain

import jakarta.persistence.*
import org.sam.lms.store.domain.persistence.AuditEntity

@Entity
class PaymentLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "status", columnDefinition = "varchar", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: PaymentStatus,

    @Column(name = "result_code", columnDefinition = "varchar", nullable = true)
    var resultCode: String,

    @Column(name = "result_message", columnDefinition = "varchar", nullable = true)
    var resultMessage: String? = "",
) : AuditEntity()