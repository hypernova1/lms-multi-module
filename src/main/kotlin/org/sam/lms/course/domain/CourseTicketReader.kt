package org.sam.lms.course.domain

import org.springframework.stereotype.Component

@Component
class CourseTicketReader(private val courseTicketRepository: CourseTicketRepository) {
    fun existsByCourseId(courseId: Long): Boolean {
        return this.courseTicketRepository.existsByCourseId(courseId)
    }

    fun existsByStudentId(studentId: Long): Boolean {
        return this.courseTicketRepository.existsByStudentId(studentId)
    }
}