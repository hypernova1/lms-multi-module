package org.sam.lms.course.domain

import org.sam.lms.course.infrastructure.persistence.entity.CourseTicketEntity

interface CourseTicketRepository {
    fun existsByCourseId(courseId: Long): Boolean
    fun existsByCourseIdAndStudentId(courseId: Long, studentId: Long): Boolean
    fun save(courseTicketEntity: CourseTicketEntity): CourseTicketEntity
    fun countByCourseId(courseId: Long): Int
    fun findByStudentId(studentId: Long): List<CourseTicketEntity>
    fun deleteByIdIn(ids: List<Long>)
}