package org.sam.lms.domain.address.domain

import org.sam.lms.domain.address.application.payload.`in`.CreateAddressDto

data class Address(
    val id: Long = 0,
    var zipcode: String,
    var si: String,
    var gugun: String,
    var doro: String,
    var detail: String,
) {
    fun fullAddress(): String {
        return "$doro $detail"
    }

    fun update(createAddressDto: CreateAddressDto) {
        this.zipcode = createAddressDto.zipcode
        this.si = createAddressDto.si
        this.gugun = createAddressDto.gugun
        this.doro  = createAddressDto.doro
        this.detail = createAddressDto.detail
    }

    companion object {
        fun from(createAccountDto: CreateAddressDto): Address {
            return Address(zipcode = createAccountDto.zipcode, si = createAccountDto.si, gugun = createAccountDto.gugun, detail = createAccountDto.detail, doro = createAccountDto.doro)
        }
    }
}