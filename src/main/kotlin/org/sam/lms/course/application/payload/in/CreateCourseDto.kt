package org.sam.lms.course.application.payload.`in`

data class CreateCourseDto(val title: String = "", val description: String = "", val categoryId: Long = 0, val price: Int = 0)