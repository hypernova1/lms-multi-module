package org.sam.lms.course.domain

import org.springframework.stereotype.Component

@Component
class CourseReader(private val courseRepository: CourseRepository) {

}