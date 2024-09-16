package org.sam.lms.address.domain

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.springframework.stereotype.Component

@Component
class AddressReader(private val addressRepository: AddressRepository) {

    fun findOne(id: Long): Address {
        val addressEntity = this.addressRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.ADDRESS_NOT_FOUND) }
        return addressEntity.toDomain()
    }

}