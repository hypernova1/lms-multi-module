package org.sam.lms.course.domain

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.course.infrastructure.persistence.entity.CategoryEntity
import org.sam.lms.course.infrastructure.persistence.entity.CourseCategoryEntity
import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CourseWriter(
    private val courseRepository: CourseRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    fun save(course: Course): Course {
        val categoryEntity = this.categoryRepository.findById(course.category.id)
            .orElseThrow { NotFoundException(ErrorCode.CATEGORY_NOT_FOUND) }
        val courseEntity = toEntity(course, categoryEntity)
        this.courseRepository.save(courseEntity)
        return course.copy(id = courseEntity.id)
    }

    @Transactional
    fun saveAll(courses: List<Course>): List<Course> {
        return courses.map { save(it) }
    }

    fun delete(id: Long) {
        this.courseRepository.deleteById(id)
    }

    private fun toEntity(course: Course, categoryEntity: CategoryEntity): CourseEntity {
        return if (course.id != 0L) {
            this.courseRepository.findById(course.id)
                .orElseThrow { NotFoundException(ErrorCode.COURSE_NOT_FOUND) }
                .apply { update(course, categoryEntity) }
        } else {
            CourseEntity.from(course).apply {
                courseCategories.add(CourseCategoryEntity(courseEntity = this, categoryEntity = categoryEntity))
            }
        }
    }

}