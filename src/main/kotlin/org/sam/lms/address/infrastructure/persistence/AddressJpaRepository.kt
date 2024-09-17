package org.sam.lms.address.infrastructure.persistence

import org.sam.lms.address.domain.AddressRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AddressJpaRepository : JpaRepository<AddressEntity, Long> {
    override fun findById(id: Long): Optional<AddressEntity>
}