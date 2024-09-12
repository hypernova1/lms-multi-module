package org.sam.lms.course.domain

interface CourseTicketRepository {
    fun existsByCourseId(courseId: Long): Boolean
}