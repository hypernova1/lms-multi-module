package org.sam.lms.store.domain.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository : JpaRepository<Order, Long>, OrderRepository {
}