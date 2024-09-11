package org.sam.lms.course.domain

import org.sam.lms.common.exception.ForbiddenException
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.`in`.UpdateCourseDto

class Course(
    val id: Long = 0L,
    var title: String,
    var description: String,
    var numberOfStudents: Int = 0,
    var category: Category,
    var price: Int = 0,
    val teacherId: Long,
) {

    fun update(updateCourseDto: UpdateCourseDto, category: Category, accountId: Long) {
        if (!this.isModified(accountId)) {
            throw ForbiddenException()
        }

        this.title = updateCourseDto.title
        this.description = updateCourseDto.description
        this.price = updateCourseDto.price
        this.category = category
    }

    private fun isModified(accountId: Long): Boolean {
        return this.teacherId == accountId;
    }

    companion object {
        fun of(createCourseDto: CreateCourseDto, category: Category, accountId: Long): Course {
            return Course(
                title = createCourseDto.title,
                description = createCourseDto.description,
                category = category,
                teacherId = accountId,
                price = createCourseDto.price,
            )
        }
    }

}