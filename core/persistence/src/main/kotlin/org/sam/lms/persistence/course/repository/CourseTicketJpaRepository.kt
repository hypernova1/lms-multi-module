package org.sam.lms.persistence.course.repository

import org.sam.lms.domain.course.domain.CourseTicket
import org.sam.lms.persistence.course.entity.CourseTicketEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseTicketJpaRepository : JpaRepository<CourseTicketEntity, Long> {

    fun existsByCourseId(courseId: Long): Boolean
    fun existsByCourseIdAndStudentId(courseId: Long, studentId: Long): Boolean
    fun save(courseTicket: CourseTicket): CourseTicket
    fun countByCourseId(courseId: Long): Int
    fun findByStudentId(studentId: Long): List<CourseTicketEntity>

}