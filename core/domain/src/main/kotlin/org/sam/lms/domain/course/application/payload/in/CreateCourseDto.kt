package org.sam.lms.domain.course.application.payload.`in`

import org.sam.lms.domain.address.application.payload.`in`.CreateAddressDto
import org.sam.lms.domain.course.domain.CourseType

class CreateCourseDto(
    val title: String = "",
    val description: String = "",
    val categoryId: Long = 0,
    val price: Int = 0,
    val type: CourseType = CourseType.ONLINE,
    val maxEnrollment: Int = 0,
    val address: CreateAddressDto? = null
)