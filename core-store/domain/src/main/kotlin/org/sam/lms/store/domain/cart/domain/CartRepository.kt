package org.sam.lms.store.domain.cart.domain

interface CartRepository {
    fun findByAccountId(accountId: Long) : Cart?
    fun save(cart: Cart): Cart
}