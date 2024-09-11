package org.sam.lms.course.infrastructure.persistence.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class CourseCategoryId(
    val courseId: Long,
    val categoryId: Long,
) : Serializable