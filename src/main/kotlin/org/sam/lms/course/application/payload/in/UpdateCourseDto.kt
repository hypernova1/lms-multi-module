package org.sam.lms.course.application.payload.`in`

import org.sam.lms.address.application.payload.`in`.AddressRequest
import org.sam.lms.course.domain.Course
import org.sam.lms.course.domain.CourseType

class UpdateCourseDto(
    val id: Long = 0,
    val title: String = "",
    val type: CourseType = CourseType.OFFLINE,
    val description: String = "",
    val categoryId: Long = 0,
    val price: Int = 0,
    val maxEnrollment: Int = 0,
    val address: AddressRequest? = null
) {
}