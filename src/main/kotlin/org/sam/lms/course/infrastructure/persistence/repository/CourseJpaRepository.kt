package org.sam.lms.course.infrastructure.persistence.repository

import org.sam.lms.course.domain.CourseRepository
import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CourseJpaRepository : JpaRepository<CourseEntity, Long>, CourseRepository {
}