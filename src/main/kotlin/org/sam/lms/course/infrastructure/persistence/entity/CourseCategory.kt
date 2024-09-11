package org.sam.lms.course.infrastructure.persistence.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class CourseCategory(
    @EmbeddedId
    val id: CourseCategoryId,

    @ManyToOne
    val courseEntity: CourseEntity,

    @ManyToOne
    val categoryEntity: CategoryEntity
)