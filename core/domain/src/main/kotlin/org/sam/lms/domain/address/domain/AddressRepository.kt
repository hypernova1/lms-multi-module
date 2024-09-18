package org.sam.lms.domain.address.domain

interface AddressRepository {
    fun findById(id: Long): Address?
    fun save(address: Address): Address
}