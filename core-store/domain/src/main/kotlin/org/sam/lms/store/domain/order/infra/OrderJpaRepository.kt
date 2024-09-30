package org.sam.lms.store.domain.order.infra

import org.sam.lms.store.domain.order.domain.Order
import org.sam.lms.store.domain.order.domain.OrderRepository
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository : JpaRepository<Order, Long>, OrderRepository {
}