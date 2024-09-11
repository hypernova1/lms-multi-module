package org.sam.lms.course.domain

import org.sam.lms.course.infrastructure.persistence.entity.CategoryEntity
import java.util.Optional

interface CategoryRepository {
    fun existsById(id: Long): Boolean
    fun findById(id: Long): Optional<CategoryEntity>
}