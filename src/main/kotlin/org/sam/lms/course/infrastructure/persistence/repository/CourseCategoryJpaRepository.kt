package org.sam.lms.course.infrastructure.persistence.repository

import org.sam.lms.course.domain.CourseCategoryRepository
import org.sam.lms.course.infrastructure.persistence.entity.CourseCategoryEntity
import org.sam.lms.course.infrastructure.persistence.entity.CourseCategoryId
import org.springframework.data.jpa.repository.JpaRepository

interface CourseCategoryJpaRepository : JpaRepository<CourseCategoryEntity, CourseCategoryId>, CourseCategoryRepository {
}