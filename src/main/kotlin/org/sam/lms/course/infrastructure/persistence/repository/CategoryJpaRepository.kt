package org.sam.lms.course.infrastructure.persistence.repository

import org.sam.lms.course.infrastructure.persistence.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryJpaRepository : JpaRepository<CategoryEntity, Long>