package org.sam.lms.address.domain

import org.sam.lms.address.infrastructure.persistence.AddressEntity
import java.util.Optional

interface AddressRepository {
    fun findById(id: Long): Optional<AddressEntity>
    fun save(addressEntity: AddressEntity): AddressEntity
}