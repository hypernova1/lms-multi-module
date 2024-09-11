package org.sam.lms.course.domain

import org.sam.lms.account.domain.Account
import org.sam.lms.course.application.payload.`in`.CreateCourseDto

class Course(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val numberOfStudents: Int = 0,
    val teacher: CourseTeacher,
    val category: Category,
) {

    companion object {
        fun of(createCourseDto: CreateCourseDto, category: Category, account: Account): Course {
            return Course(
                title = createCourseDto.title,
                description = createCourseDto.description,
                category = category,
                teacher = CourseTeacher(account.id, account.name)
            )
        }
    }

}