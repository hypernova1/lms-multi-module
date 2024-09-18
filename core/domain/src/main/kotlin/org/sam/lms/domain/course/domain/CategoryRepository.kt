package org.sam.lms.domain.course.domain

interface CategoryRepository {
    fun existsById(id: Long): Boolean
    fun findById(id: Long): org.sam.lms.domain.course.domain.Category?
    fun findAll(): List<org.sam.lms.domain.course.domain.Category>
}