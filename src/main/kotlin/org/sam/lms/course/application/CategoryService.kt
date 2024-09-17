package org.sam.lms.course.application

import org.sam.lms.course.domain.Category
import org.sam.lms.course.domain.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun existsById(id: Long): Boolean {
        return this.categoryRepository.existsById(id)
    }

    fun findAll(): List<Category> {
        return this.categoryRepository.findAll()
    }
}