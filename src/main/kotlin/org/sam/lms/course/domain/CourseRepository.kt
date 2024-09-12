package org.sam.lms.course.domain

import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CourseRepository {
    fun save(courseEntity: CourseEntity): CourseEntity

    @Query("SELECT course, category " +
            "FROM CourseEntity course " +
            "JOIN FETCH course.courseCategories courseCategory " +
            "JOIN FETCH courseCategory.categoryEntity category " +
            "WHERE course.id = :id")
    fun findById(id: Long): Optional<CourseEntity>

    fun deleteById(id: Long)
}