package org.sam.lms.domain.course.application.payload.out

class CourseDetailView(
    val id: Long,
    val title: String,
    val description: String,
    val price: Long,
    val categoryId: Long,
    val categoryName: String,
    val accountId: Long,
    val teacherName: String,
    val maxEnrollments: Int,
)