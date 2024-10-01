package org.sam.lms.domain.course.domain

import org.sam.lms.domain.common.Page
import org.sam.lms.domain.common.Paging
import org.sam.lms.domain.course.application.payload.out.CourseDetailView
import org.sam.lms.domain.course.application.payload.out.CourseSummaryView


interface CourseRepository {
    fun save(course: Course): Course
    fun findById(id: Long): Course?
    fun findDetailView(id: Long): CourseDetailView?
    fun deleteById(id: Long)
    fun findSummaryView(status: CourseStatus, pageable: Paging): Page<CourseSummaryView>
    fun findByIdsWithPessimisticLock(ids: List<Long>): List<Course>
    fun saveAll(courses: List<Course>): List<Course>
    fun existsById(id: Long): Boolean
    fun findDetailViewList(ids: List<Long>): List<CourseDetailView>
}