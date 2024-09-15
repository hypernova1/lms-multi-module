package org.sam.lms.course.domain

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.springframework.stereotype.Component

@Component
class CourseReader(private val courseRepository: CourseRepository) {
    fun findOne(id: Long): Course {
        val course = this.courseRepository.findById(id).orElseThrow { NotFoundException(ErrorCode.COURSE_NOT_FOUND) }
        return course.toDomain()
    }

    fun findAllWithLock(ids: List<Long>): List<Course> {
        return this.courseRepository.findByIdsWithPessimisticLock(ids).map { it.toDomain() }
    }

}