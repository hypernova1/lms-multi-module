package org.sam.lms.domain.course.domain

interface CourseTicketRepository {
    fun existsByCourseId(courseId: Long): Boolean
    fun existsByCourseIdAndStudentId(courseId: Long, studentId: Long): Boolean
    fun save(courseTicket: CourseTicket): CourseTicket
    fun countByCourseId(courseId: Long): Int
    fun findByStudentId(studentId: Long): List<CourseTicket>
    fun deleteByIdIn(ids: List<Long>)
    fun findByCourseIdAndStudentId(courseId: Long, studentId: Long): CourseTicket?
    fun deleteByCourseIdAndStudentId(courseId: Long, studentId: Long)
}