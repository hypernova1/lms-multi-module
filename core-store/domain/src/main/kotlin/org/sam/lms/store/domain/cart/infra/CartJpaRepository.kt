package org.sam.lms.store.domain.cart.infra

import org.sam.lms.store.domain.cart.domain.Cart
import org.sam.lms.store.domain.cart.domain.CartRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CartJpaRepository : JpaRepository<Cart, Long>, CartRepository {

    @Query("""
        SELECT c FROM Cart c JOIN FETCH c.items item WHERE c.accountId = :accountId
    """)
    override fun findByAccountId(accountId: Long) : Cart?


}