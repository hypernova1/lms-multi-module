package org.sam.lms.address.application

import org.sam.lms.address.application.payload.`in`.AddressRequest
import org.sam.lms.address.application.payload.out.AddressResponse
import org.sam.lms.address.domain.Address
import org.sam.lms.address.domain.AddressRepository
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class AddressService(private val addressRepository: AddressRepository) {

    /**
     * 주소를 저장한다. 요청 데이터에 아이디가 존재하면 업데이트한다.
     *
     * @param addressRequest 요청 정보
     * @return 저장된 주소 정보
     * */
    fun save(addressRequest: AddressRequest): AddressResponse {
        val address = addressRequest.id?.let {
            this.addressRepository.findById(it)?.apply { update(addressRequest) }
                ?: throw NotFoundException(ErrorCode.ADDRESS_NOT_FOUND)
        } ?: Address.from(addressRequest)

        val savedAddress = this.addressRepository.save(address)
        return AddressResponse(
            id = savedAddress.id,
            zipcode = savedAddress.zipcode,
            si = savedAddress.si,
            gugun = savedAddress.gugun,
            doro = savedAddress.doro,
            detail = savedAddress.detail
        )
    }

    fun findOne(id: Long): Address {
        val address = this.addressRepository.findById(id) ?: throw NotFoundException(ErrorCode.ADDRESS_NOT_FOUND)
        return address
    }

}