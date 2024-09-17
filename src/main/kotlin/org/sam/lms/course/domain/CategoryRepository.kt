package org.sam.lms.course.domain

interface CategoryRepository {
    fun existsById(id: Long): Boolean
    fun findById(id: Long): Category?
    fun findAll(): List<Category>
}