package org.sam.lms.store.domain.order.domain

import jakarta.persistence.*
import org.sam.lms.store.domain.persistence.AuditEntity

@Entity
class OrderLine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "course_id", columnDefinition = "bigint", nullable = false)
    val courseId: Long,

    @ManyToOne
    @JoinColumn(name = "order_no", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val order: Order

) : AuditEntity() {

}