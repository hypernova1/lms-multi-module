package org.sam.lms.domain.course.domain

import org.springframework.stereotype.Component

@Component
class CourseTicketWriter(private val courseTicketRepository: CourseTicketRepository) {

    fun save(courseTicket: CourseTicket): CourseTicket {
        return courseTicketRepository.save(courseTicket)
    }

    fun deleteAll(ids: List<Long>) {
        this.courseTicketRepository.deleteByIdIn(ids)
    }

    fun delete(courseTicket: CourseTicket) {
        this.courseTicketRepository.delete(courseTicket)
    }

}