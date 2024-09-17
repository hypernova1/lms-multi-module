package org.sam.lms.address.domain

interface AddressRepository {
    fun findById(id: Long): Address?
    fun save(address: Address): Address
}