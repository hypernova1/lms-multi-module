package org.sam.lms.course.domain

import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity

interface CourseRepository {
    fun save(courseEntity: CourseEntity): CourseEntity
}