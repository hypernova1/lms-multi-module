package org.sam.lms.address.application.payload.`in`

class AddressRequest(
    val id: Long? = null,
    val si: String = "",
    val gugun: String = "",
    val doro: String = "",
    val detail: String = "",
    val zipcode: String = ""
)