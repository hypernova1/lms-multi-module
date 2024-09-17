package org.sam.lms.course.domain

import org.springframework.stereotype.Component

@Component
class CourseTicketWriter(private val courseTicketRepository: CourseTicketRepository) {

    fun save(courseTicket: CourseTicket): CourseTicket {
        return courseTicketRepository.save(courseTicket)
    }

    fun deleteAll(ids: List<Long>) {
        this.courseTicketRepository.deleteByIdIn(ids)
    }

}