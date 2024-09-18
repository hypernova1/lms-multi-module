package org.sam.lms.domain.course.application.payload.out

data class CourseSummaryView(
    val id: Long,
    val title: String,
    val price: Int,
    val maxEnrollments: Int,
    val teacherName: String
) {
}