package org.sam.lms.course.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class CourseCategoryId(
    @Column(name = "course_id")
    val courseId: Long = 0,
    @Column(name = "category_id")
    var categoryId: Long = 0,
) : Serializable