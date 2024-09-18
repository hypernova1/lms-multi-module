package org.sam.lms.domain.course.domain

import java.time.LocalDateTime

data class CourseTicket(
    val id: Long = 0L,
    val courseId: Long,
    val studentId: Long,
) {
    val applicationDate: LocalDateTime = LocalDateTime.now()
}