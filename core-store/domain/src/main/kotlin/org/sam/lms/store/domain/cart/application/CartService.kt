package org.sam.lms.store.domain.cart.application

import org.sam.lms.store.domain.cart.application.`in`.AddCartItemDto
import org.sam.lms.store.domain.cart.domain.Cart
import org.sam.lms.store.domain.cart.domain.CartRepository
import org.sam.lms.store.domain.provider.Provider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartService(private val cartRepository: CartRepository) {

    @Transactional
    fun addItem(addCartItemDto: AddCartItemDto, provider: Provider) {
        val cart = this.findOne(provider.id)
        cart.addItem(addCartItemDto.courseId)
        this.cartRepository.save(cart)
    }

    fun findOne(accountId: Long): Cart {
        val cart = this.cartRepository.findByAccountId(accountId) ?: Cart(accountId = accountId)
        this.cartRepository.save(cart)
        return cart
    }

}