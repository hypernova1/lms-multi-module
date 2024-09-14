package org.sam.lms.address.domain

import org.sam.lms.address.application.payload.`in`.AddressRequest

class Address(
    var id: Long = 0,
    var zipcode: String,
    var si: String,
    var gugun: String,
    var doro: String,
    var detail: String,
) {
    fun fullAddress(): String {
        return "$doro $detail"
    }

    fun update(addressRequest: AddressRequest) {
        this.zipcode = addressRequest.zipcode
        this.si = addressRequest.si
        this.gugun = addressRequest.gugun
        this.doro  = addressRequest.doro
        this.detail = addressRequest.detail
    }

    companion object {
        fun from(addressRequest: AddressRequest): Address {
            return Address(zipcode = addressRequest.zipcode, si = addressRequest.si, gugun = addressRequest.gugun, detail = addressRequest.detail, doro = addressRequest.doro)
        }
    }
}