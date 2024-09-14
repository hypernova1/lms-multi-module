package org.sam.lms.address.domain

import org.sam.lms.address.infrastructure.persistence.AddressEntity
import org.springframework.stereotype.Component

@Component
class AddressProcessor(private val addressRepository: AddressRepository) {

    fun save(address: Address): Address {
        val addressEntity = toEntity(address)
        this.addressRepository.save(addressEntity)

        address.id = addressEntity.id
        return address;
    }

    private fun toEntity(address: Address): AddressEntity {
        return AddressEntity.from(address)
    }

}