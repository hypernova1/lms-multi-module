package org.sam.lms.course.infrastructure.persistence.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table

@Table(name = "course_category")
@Entity
class CourseCategoryEntity(
    @EmbeddedId
    val id: CourseCategoryId = CourseCategoryId(),

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    val courseEntity: CourseEntity,

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    val categoryEntity: CategoryEntity
) {
    fun update(categoryEntity: CategoryEntity) {
        this.id.categoryId = categoryEntity.id
    }
}