package org.sam.lms.course.domain

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.course.infrastructure.persistence.entity.CategoryEntity
import org.sam.lms.course.infrastructure.persistence.entity.CourseCategoryEntity
import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity
import org.springframework.stereotype.Component

@Component
class CourseProcessor(private val courseRepository: CourseRepository, private val categoryRepository: CategoryRepository) {

    fun save(course: Course): Course {
        val categoryEntity = this.categoryRepository.findById(course.category.id)
            .orElseThrow { NotFoundException(ErrorCode.CATEGORY_NOT_FOUND) }
        val courseEntity = toEntity(course, categoryEntity)
        this.courseRepository.save(courseEntity)
        course.id = courseEntity.id
        return course
    }

    fun delete(id: Long) {
        this.courseRepository.deleteById(id)
    }

    private fun toEntity(course: Course, categoryEntity: CategoryEntity): CourseEntity {
        val courseEntity: CourseEntity
        if (course.id != 0L) {
            courseEntity = this.courseRepository.findById(course.id)
                .orElseThrow { NotFoundException(ErrorCode.COURSE_NOT_FOUND) }
            courseEntity.update(course, categoryEntity)
        } else {
            courseEntity =
                CourseEntity(title = course.title, description = course.description, accountId = course.teacherId)
            courseEntity.courseCategories.add(CourseCategoryEntity(courseEntity = courseEntity, categoryEntity = categoryEntity))
        }
        return courseEntity
    }

}