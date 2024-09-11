package org.sam.lms.course.application

import org.sam.lms.account.domain.Account
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.out.CourseSummary
import org.sam.lms.course.domain.CategoryReader
import org.sam.lms.course.domain.Course
import org.sam.lms.course.domain.CourseProcessor
import org.sam.lms.course.domain.CourseReader
import org.springframework.stereotype.Service

@Service
class CourseService(private val courseReader: CourseReader, private val courseProcessor: CourseProcessor, private val categoryReader: CategoryReader) {
    fun create(createCourseDto: CreateCourseDto, account: Account): CourseSummary {
        val category = this.categoryReader.findById(createCourseDto.categoryId)
        val course = Course.of(createCourseDto, category, account);
        this.courseProcessor.save(course)
        return CourseSummary(id = course.id, title = course.title)
    }
}