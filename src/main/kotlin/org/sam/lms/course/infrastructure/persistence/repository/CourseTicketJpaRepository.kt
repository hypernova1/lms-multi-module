package org.sam.lms.course.infrastructure.persistence.repository

import org.sam.lms.course.domain.CourseTicket
import org.sam.lms.course.infrastructure.persistence.entity.CourseTicketEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseTicketJpaRepository : JpaRepository<CourseTicketEntity, Long> {

    fun existsByCourseId(courseId: Long): Boolean
    fun existsByCourseIdAndStudentId(courseId: Long, studentId: Long): Boolean
    fun save(courseTicket: CourseTicket): CourseTicket
    fun countByCourseId(courseId: Long): Int
    fun findByStudentId(studentId: Long): List<CourseTicketEntity>

}