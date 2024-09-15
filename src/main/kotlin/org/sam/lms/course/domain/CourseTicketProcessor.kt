package org.sam.lms.course.domain

import org.sam.lms.course.infrastructure.persistence.entity.CourseTicketEntity
import org.springframework.stereotype.Component

@Component
class CourseTicketProcessor(private val courseTicketRepository: CourseTicketRepository) {

    fun save(courseTicket: CourseTicket): CourseTicket {
        val courseTicketEntity = toEntity(courseTicket)
        this.courseTicketRepository.save(courseTicketEntity)
        courseTicket.id = courseTicketEntity.id
        courseTicket.applicationDate = courseTicketEntity.applicationDate
        return courseTicket
    }

    private fun toEntity(courseTicket: CourseTicket): CourseTicketEntity {
        return CourseTicketEntity(studentId = courseTicket.studentId, courseId = courseTicket.courseId, applicationDate = courseTicket.applicationDate)
    }

    fun deleteAll(ids: List<Long>) {
        this.courseTicketRepository.deleteByIdIn(ids)
    }

}