package org.sam.lms.course.domain

import org.sam.lms.common.exception.BadRequestException
import org.sam.lms.common.exception.ConflictException
import org.sam.lms.common.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class CourseTicketReader(private val courseTicketRepository: CourseTicketRepository) {
    fun checkEnrolledStudents(courseId: Long) {
        val existTickets =  this.courseTicketRepository.existsByCourseId(courseId)
        if (existTickets) {
            throw BadRequestException(ErrorCode.EXISTS_STUDENTS)
        }
    }

    fun checkAlreadyEnrolled(studentId: Long) {
        val alreadyEnrolled = this.courseTicketRepository.existsByStudentId(studentId)
        if (alreadyEnrolled) {
            throw ConflictException(ErrorCode.ALREADY_JOINED_STUDENT)
        }
    }
}