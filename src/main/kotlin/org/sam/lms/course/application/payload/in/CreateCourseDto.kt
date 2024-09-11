package org.sam.lms.course.application.payload.`in`

data class CreateCourseDto(val title: String, val description: String, val categoryId: Long, val price: Int)