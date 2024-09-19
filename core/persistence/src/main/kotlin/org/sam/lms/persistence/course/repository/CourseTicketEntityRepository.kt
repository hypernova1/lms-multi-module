package org.sam.lms.persistence.course.repository

import org.sam.lms.domain.course.domain.CourseTicket
import org.sam.lms.domain.course.domain.CourseTicketRepository
import org.sam.lms.persistence.course.entity.CourseTicketEntity
import org.springframework.stereotype.Repository

@Repository
class CourseTicketEntityRepository(private val courseTicketJpaRepository: CourseTicketJpaRepository) : CourseTicketRepository {
    override fun existsByCourseId(courseId: Long): Boolean {
        return this.courseTicketJpaRepository.existsByCourseId(courseId)
    }

    override fun existsByCourseIdAndStudentId(courseId: Long, studentId: Long): Boolean {
        return this.courseTicketJpaRepository.existsByCourseIdAndStudentId(courseId, studentId)
    }

    override fun save(courseTicket: CourseTicket): CourseTicket {
            val courseTicketEntity = toEntity(courseTicket)
            this.courseTicketJpaRepository.save(courseTicketEntity)
            return courseTicket.copy(id = courseTicketEntity.id)
    }

    override fun countByCourseId(courseId: Long): Int {
        return this.courseTicketJpaRepository.countByCourseId(courseId)
    }

    override fun findByStudentId(studentId: Long): List<CourseTicket> {
        return this.courseTicketJpaRepository.findByStudentId(studentId).map { it.toDomain() }
    }

    override fun deleteByIdIn(ids: List<Long>) {
        return this.courseTicketJpaRepository.deleteAllById(ids)
    }

    private fun toEntity(courseTicket: CourseTicket): CourseTicketEntity {
        return CourseTicketEntity(studentId = courseTicket.studentId, courseId = courseTicket.courseId, applicationDate = courseTicket.applicationDate)
    }
}