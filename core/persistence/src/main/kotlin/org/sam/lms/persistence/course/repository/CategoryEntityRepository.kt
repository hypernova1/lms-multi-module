package org.sam.lms.persistence.course.repository

import org.sam.lms.domain.course.domain.Category
import org.sam.lms.domain.course.domain.CategoryRepository
import org.sam.lms.persistence.course.entity.CategoryEntity
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

    override fun save(category: Category): Category {
        val categoryEntity = toEntity(category)
        this.categoryJpaRepository.save(categoryEntity)
        return category.copy(id = categoryEntity.id)
    }

    fun toEntity(category: Category): CategoryEntity {
        return CategoryEntity.from(category)
    }

}