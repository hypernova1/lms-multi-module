package org.sam.lms.course.domain

import org.sam.lms.common.Page
import org.sam.lms.common.Paging
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.course.application.payload.out.CourseDetailView
import org.sam.lms.course.application.payload.out.CourseSummaryView
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class CourseReader(private val courseRepository: CourseRepository) {
    fun findOne(id: Long): Course {
        val course = this.courseRepository.findById(id).orElseThrow { NotFoundException(ErrorCode.COURSE_NOT_FOUND) }
        return course.toDomain()
    }

    fun findAllWithLock(ids: List<Long>): List<Course> {
        return this.courseRepository.findByIdsWithPessimisticLock(ids).map { it.toDomain() }
    }

    fun findAllPaging(paging: Paging): Page<CourseSummaryView> {
        val courseEntityPage =
            this.courseRepository.findSummaryView(
                CourseStatus.VISIBLE,
                PageRequest.of(paging.page - 1, paging.size, Sort.by(Sort.Direction.DESC, "id"))
            )

        return Page(page = paging.page, size = paging.size, totalPage = courseEntityPage.totalPages, items = courseEntityPage.content)
    }

    fun findDetail(id: Long): CourseDetailView? {
        return this.courseRepository.findDetailView(id).orElse(null)
    }

}