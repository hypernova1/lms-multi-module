package org.sam.lms.store.domain.cart.domain

import jakarta.persistence.*
import org.sam.lms.store.domain.persistence.AuditEntity
import org.springframework.boot.context.properties.bind.DefaultValue
import java.time.LocalDateTime

@Entity
class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "course_id", columnDefinition = "bigint", nullable = false)
    val courseId: Long,

    @ManyToOne
    @JoinColumn(name = "cart_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val cart: Cart,

    @DefaultValue("current_timestamp")
    @Column(name = "added_date", columnDefinition = "timestamp")
    val addedDate: LocalDateTime = LocalDateTime.now()

) : AuditEntity()