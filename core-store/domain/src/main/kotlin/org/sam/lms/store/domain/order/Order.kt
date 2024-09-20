package org.sam.lms.store.domain.order

import jakarta.persistence.*

@Table(name = "orders")
@Entity
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
) {
}