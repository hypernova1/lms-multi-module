package org.sam.lms.course.infrastructure.persistence

import org.sam.lms.course.domain.CourseRepository
import org.springframework.data.jpa.repository.JpaRepository

interface CourseJpaRepository : JpaRepository<CourseJpaEntity, Long>, CourseRepository {
}