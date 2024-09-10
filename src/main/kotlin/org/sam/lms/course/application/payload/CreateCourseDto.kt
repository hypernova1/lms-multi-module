package org.sam.lms.course.application.payload

data class CreateCourseDto(val title: String, val description: String, val categoryId: Long)