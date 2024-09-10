package org.sam.lms.course.application

import org.sam.lms.course.application.payload.CreateCourseDto
import org.sam.lms.course.domain.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(private val courseRepository: CourseRepository) {
    fun create(createCourseDto: CreateCourseDto) {

    }
}