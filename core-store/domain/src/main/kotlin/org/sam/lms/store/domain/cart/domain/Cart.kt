package org.sam.lms.store.domain.cart.domain

import jakarta.persistence.*
import org.sam.lms.common.exception.ConflictException
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.store.domain.persistence.AuditEntity
import org.springframework.web.client.HttpClientErrorException.Conflict

@Table(name = "cart", indexes = [
    Index(name = "cart_account_id_idx", columnList = "account_id")
])
@Entity
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "account_id", columnDefinition = "bigint", nullable = false)
    val accountId: Long,

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL])
    val items: MutableList<CartItem> = mutableListOf()
) :AuditEntity() {

    fun addItem(courseId: Long) {
        if (this.items.any() { it.courseId == courseId }) {
            throw ConflictException(ErrorCode.ALREADY_ADDED_CART_ITEM)
        }

        this.items.add(CartItem(courseId = courseId, cart = this))
    }

    fun isEmpty(): Boolean {
        return this.items.isEmpty()
    }

    fun clear() {
        this.items.clear()
    }

}