package org.sam.lms.course.application

import org.sam.lms.account.domain.Account
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.`in`.UpdateCourseDto
import org.sam.lms.course.application.payload.out.CourseSummary
import org.sam.lms.course.domain.CategoryReader
import org.sam.lms.course.domain.Course
import org.sam.lms.course.domain.CourseProcessor
import org.sam.lms.course.domain.CourseReader
import org.springframework.stereotype.Service

@Service
class CourseService(private val courseReader: CourseReader, private val courseProcessor: CourseProcessor, private val categoryReader: CategoryReader) {

    /**
     * 강의 정보를 등록한다.
     *
     * @param createCourseDto 강의 정보
     * @param accountId 등록자 아이디
     * @return 강의 요약 정보
     * */
    fun create(createCourseDto: CreateCourseDto, accountId: Long): CourseSummary {
        val category = this.categoryReader.findById(createCourseDto.categoryId)
        val course = Course.of(createCourseDto, category, accountId);
        this.courseProcessor.save(course)
        return CourseSummary(id = course.id, title = course.title)
    }

    /**
     * 강의 정보를 수정한다.
     *
     * @param updateCourseDto 수정 정보
     * @param accountId
     * */
    fun update(updateCourseDto: UpdateCourseDto, accountId: Long): CourseSummary {
        val course = this.courseReader.findById(updateCourseDto.id)
        val category = this.categoryReader.findById(updateCourseDto.categoryId)
        course.update(updateCourseDto, category, accountId)
        return CourseSummary(id = course.id, title = course.title)
    }
}