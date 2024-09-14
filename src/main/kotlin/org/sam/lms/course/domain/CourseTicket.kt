package org.sam.lms.course.domain

import java.time.LocalDateTime

class CourseTicket(
    var id: Long = 0,
    val courseId: Long,
    val studentId: Long,
    var applicationDate: LocalDateTime = LocalDateTime.now(),
) {
}