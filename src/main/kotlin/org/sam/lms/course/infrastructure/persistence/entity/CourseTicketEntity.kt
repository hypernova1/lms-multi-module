package org.sam.lms.course.infrastructure.persistence.entity

import jakarta.persistence.*
import org.sam.lms.infra.persistence.AuditEntity
import java.time.LocalDateTime

@Table(name = "course_ticket")
@Entity
class CourseTicketEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val studentId: Long,
    val courseId: Long,
    val applicationDate: LocalDateTime = LocalDateTime.now(),
) : AuditEntity() {
}