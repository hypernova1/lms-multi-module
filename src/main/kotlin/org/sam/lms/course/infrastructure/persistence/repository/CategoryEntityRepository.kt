package org.sam.lms.course.infrastructure.persistence.repository

import org.sam.lms.course.domain.Category
import org.sam.lms.course.domain.CategoryRepository
import org.springframework.stereotype.Repository

@Repository
class CategoryEntityRepository(private val categoryJpaRepository: CategoryJpaRepository) : CategoryRepository {

    override fun existsById(id: Long): Boolean {
        return this.categoryJpaRepository.existsById(id)
    }

    override fun findById(id: Long): Category {
        val categoryEntity = this.categoryJpaRepository.findById(id).orElse(null)
        return categoryEntity.toDomain()
    }

    override fun findAll(): List<Category> {
        return this.categoryJpaRepository.findAll().map { it.toDomain() }
    }

}