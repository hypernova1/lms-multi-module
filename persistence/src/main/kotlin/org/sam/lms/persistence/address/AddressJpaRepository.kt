package org.sam.lms.persistence.address

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AddressJpaRepository : JpaRepository<AddressEntity, Long> {
    override fun findById(id: Long): Optional<AddressEntity>
}