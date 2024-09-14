package org.sam.lms.course.domain

import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity
import org.springframework.stereotype.Component

@Component
class CourseProcessor(private val courseRepository: CourseRepository) {

    fun save(course: Course) {
        this.courseRepository.save(toEntity(course))
    }

    fun delete(id: Long) {
        this.courseRepository.deleteById(id)
    }

    private fun toEntity(course: Course): CourseEntity {
        return CourseEntity(title = course.title, description = course.description, accountId = course.teacherId)
    }

}