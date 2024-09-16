package org.sam.lms.address.application

import org.sam.lms.address.application.payload.`in`.AddressRequest
import org.sam.lms.address.application.payload.out.AddressResponse
import org.sam.lms.address.domain.Address
import org.sam.lms.address.domain.AddressWriter
import org.sam.lms.address.domain.AddressReader
import org.springframework.stereotype.Service

@Service
class AddressService(private val addressReader: AddressReader, private val addressWriter: AddressWriter) {

    /**
     * 주소를 저장한다. 요청 데이터에 아이디가 존재하면 업데이트한다.
     *
     * @param addressRequest 요청 정보
     * @return 저장된 주소 정보
     * */
    fun save(addressRequest: AddressRequest): AddressResponse {
        val address = addressRequest.id?.let {
            this.addressReader.findOne(it).apply { update(addressRequest) }
        } ?: Address.from(addressRequest)

        this.addressWriter.save(address)
        return AddressResponse(
            id = address.id,
            zipcode = address.zipcode,
            si = address.si,
            gugun = address.gugun,
            doro = address.doro,
            detail = address.detail
        )
    }

    fun findOne(id: Long): Address {
        return this.addressReader.findOne(id)
    }

}