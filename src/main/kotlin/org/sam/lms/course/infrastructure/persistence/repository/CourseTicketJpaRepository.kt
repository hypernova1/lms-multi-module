package org.sam.lms.course.infrastructure.persistence.repository

import org.sam.lms.course.domain.CourseTicketRepository
import org.sam.lms.course.infrastructure.persistence.entity.CourseTicketEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseTicketJpaRepository : JpaRepository<CourseTicketEntity, Long>, CourseTicketRepository {
}