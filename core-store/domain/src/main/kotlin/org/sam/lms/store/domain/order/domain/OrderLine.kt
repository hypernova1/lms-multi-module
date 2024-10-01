package org.sam.lms.store.domain.order.domain

import jakarta.persistence.*
import jakarta.persistence.ForeignKey
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.*
import org.sam.lms.store.domain.persistence.AuditEntity

@FilterDef(name = "deletedOrderLineFilter", parameters = [ParamDef(name = "deletedDate", type = Boolean::class)])
@Filter(name = "deletedOrderLineFilter", condition = "deleted_date IS NULL")
@SQLDelete(sql = "UPDATE order_line SET deleted_date = current_timestamp WHERE id = ?")
@SQLRestriction("deleted_date is null")
@Table(name = "order_line", indexes = [Index(name = "order_line_order_no_idx", columnList = "order_no")])
@Entity
class OrderLine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "course_id", columnDefinition = "bigint", nullable = false)
    val courseId: Long,

    @Column(name = "price", columnDefinition = "integer", nullable = false)
    val price: Int = 0,

    @ManyToOne
    @JoinColumn(name = "order_no", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val order: Order

) : AuditEntity() {

}