package org.sam.lms.store.api.cart

import jakarta.validation.Valid
import org.sam.lms.store.api.cart.request.AddCartProductRequest
import org.sam.lms.store.domain.cart.application.CartService
import org.sam.lms.store.domain.cart.application.`in`.AddCartItemDto
import org.sam.lms.store.domain.provider.Provider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/store/api/v1/carts")
class CartController(private val cartService: CartService) {

    @PostMapping("/products")
    fun addProducts(
        @Valid @RequestBody addCartProductRequest: AddCartProductRequest,
        provider: Provider
    ): ResponseEntity<Void> {
        cartService.addItem(AddCartItemDto(courseId = addCartProductRequest.courseId), provider)
        return ResponseEntity.ok().build()
    }

}
