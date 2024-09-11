package org.sam.lms.course.domain

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.springframework.stereotype.Component

@Component
class CategoryReader(private val categoryRepository: CategoryRepository) {

    fun existsById(id: Long): Boolean {
        return this.categoryRepository.existsById(id)
    }

    fun findById(id: Long): Category {
        val categoryEntity = this.categoryRepository.findById(id).orElseThrow { NotFoundException(ErrorCode.CATEGORY_NOT_FOUND) }
        return categoryEntity.toDomain()
    }

}