package org.sam.lms.persistence.course.repository

import org.sam.lms.domain.course.domain.Category
import org.springframework.stereotype.Repository

@Repository
class CategoryEntityRepository(private val categoryJpaRepository: CategoryJpaRepository) :
    org.sam.lms.domain.course.domain.CategoryRepository {

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