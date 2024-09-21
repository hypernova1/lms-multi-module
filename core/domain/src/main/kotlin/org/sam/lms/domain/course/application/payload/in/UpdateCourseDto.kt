package org.sam.lms.domain.course.application.payload.`in`

import org.sam.lms.domain.address.application.payload.`in`.CreateAddressDto
import org.sam.lms.domain.course.domain.CourseType

class UpdateCourseDto(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val type: CourseType = CourseType.OFFLINE,
    val categoryId: Long = 0,
    val price: Int = 0,
    val maxEnrollment: Int = 0,
    val address: CreateAddressDto? = null
)