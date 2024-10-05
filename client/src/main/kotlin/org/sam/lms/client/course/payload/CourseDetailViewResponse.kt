package org.sam.lms.client.course.payload

class CourseDetailViewResponse(
    val id: Long,
    val title: String,
    val price: Int,
    val maxEnrollments: Int,
    val numberOfStudents: Int,
) {
}