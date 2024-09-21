package org.sam.lms.domain.address.application

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.domain.address.application.payload.`in`.CreateAddressDto
import org.sam.lms.domain.address.application.payload.out.AddressResponse
import org.sam.lms.domain.address.domain.Address
import org.sam.lms.domain.address.domain.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(private val addressRepository: AddressRepository) {

    /**
     * 주소를 저장한다. 요청 데이터에 아이디가 존재하면 업데이트한다.
     *
     * @param createAddressDto 요청 정보
     * @return 저장된 주소 정보
     * */
    fun save(createAddressDto: CreateAddressDto): AddressResponse {
        val address = createAddressDto.id?.let {
            this.addressRepository.findById(it)?.apply { update(createAddressDto) }
                ?: throw NotFoundException(ErrorCode.ADDRESS_NOT_FOUND)
        } ?: Address.from(createAddressDto)

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