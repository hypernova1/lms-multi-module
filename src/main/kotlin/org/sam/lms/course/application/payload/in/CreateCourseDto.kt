package org.sam.lms.course.application.payload.`in`

import org.sam.lms.address.application.payload.`in`.AddressRequest
import org.sam.lms.course.domain.CourseType

data class CreateCourseDto(
    val title: String = "",
    val description: String = "",
    val categoryId: Long = 0,
    val price: Int = 0,
    val type: CourseType = CourseType.ONLINE,
    val maxEnrollment: Int = 0,
    val address: AddressRequest? = null
)