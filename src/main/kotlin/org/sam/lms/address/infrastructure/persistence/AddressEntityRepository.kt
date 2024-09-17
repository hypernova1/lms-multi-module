package org.sam.lms.address.infrastructure.persistence

import org.sam.lms.address.domain.Address
import org.sam.lms.address.domain.AddressRepository
import org.springframework.stereotype.Repository

@Repository
class AddressEntityRepository(private val addressJpaRepository: AddressJpaRepository) : AddressRepository {
    override fun findById(id: Long): Address? {
        val addressEntity = this.addressJpaRepository.findById(id)
            .orElse(null)
        return addressEntity?.toDomain()
    }

    override fun save(address: Address): Address {
        val addressEntity = toEntity(address)
        this.addressJpaRepository.save(addressEntity)
        return address.copy(id = addressEntity.id)
    }

    private fun toEntity(address: Address): AddressEntity {
        return AddressEntity.from(address)
    }
}