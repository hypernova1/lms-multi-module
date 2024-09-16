package org.sam.lms.course.domain

import org.sam.lms.course.application.payload.out.CourseDetailView
import org.sam.lms.course.application.payload.out.CourseSummaryView
import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface CourseRepository {
    fun save(courseEntity: CourseEntity): CourseEntity
    fun findById(id: Long): Optional<CourseEntity>
    fun findDetailView(id: Long): Optional<CourseDetailView>
    fun deleteById(id: Long)
    fun findByIdsWithPessimisticLock(ids: List<Long>): List<CourseEntity>
    fun findSummaryView(status: CourseStatus, pageable: Pageable): Page<CourseSummaryView>
}