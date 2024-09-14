package org.sam.lms.address.infrastructure.persistence

import org.sam.lms.address.domain.AddressRepository
import org.springframework.data.jpa.repository.JpaRepository

interface AddressJpaRepository : JpaRepository<AddressEntity, Long>, AddressRepository {
}