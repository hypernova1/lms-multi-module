package org.sam.lms.persistence.course.repository

import org.sam.lms.persistence.course.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryJpaRepository : JpaRepository<CategoryEntity, Long>