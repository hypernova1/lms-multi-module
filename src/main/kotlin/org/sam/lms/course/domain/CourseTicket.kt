package org.sam.lms.course.domain

class CourseTicket(
    var id: Long = 0,
    val courseId: Long,
    val studentId: Long
) {
}