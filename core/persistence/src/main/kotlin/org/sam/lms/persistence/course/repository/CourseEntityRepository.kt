package org.sam.lms.persistence.course.repository

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.domain.common.Page
import org.sam.lms.domain.common.Paging
import org.sam.lms.domain.course.application.payload.out.CourseDetailView
import org.sam.lms.domain.course.application.payload.out.CourseSummaryView
import org.sam.lms.domain.course.domain.Course
import org.sam.lms.domain.course.domain.CourseRepository
import org.sam.lms.domain.course.domain.CourseStatus
import org.sam.lms.persistence.course.entity.CourseEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class CourseEntityRepository(private val courseJpaRepository: CourseJpaRepository) : CourseRepository {

    override fun findById(id: Long): Course? {
        val course = this.courseJpaRepository.findById(id).orElse(null)
        return course?.toDomain()
    }

    override fun findByIdsWithPessimisticLock(ids: List<Long>): List<Course> {
        return this.courseJpaRepository.findByIdsWithPessimisticLock(ids).map { it.toDomain() }
    }

    override fun findSummaryView(
        status: CourseStatus,
        pageable: Paging
    ): Page<CourseSummaryView> {
        val courseEntityPage =
            this.courseJpaRepository.findSummaryView(
                status,
                PageRequest.of(pageable.page - 1, pageable.size, Sort.by(Sort.Direction.DESC, "id"))
            )

        return Page(
            page = pageable.page,
            size = pageable.size,
            totalPage = courseEntityPage.totalPages,
            items = courseEntityPage.content
        )
    }

    override fun findDetailView(id: Long): CourseDetailView? {
        return this.courseJpaRepository.findDetailView(id).orElse(null)
    }

    @Transactional
    override fun save(course: Course): Course {
        val courseEntity = toEntity(course)
        this.courseJpaRepository.save(courseEntity)
        return course.copy(id = courseEntity.id)
    }

    @Transactional
    override fun saveAll(courses: List<Course>): List<Course> {
        return courses.map { save(it) }
    }

    override fun existsById(id: Long): Boolean {
        return this.courseJpaRepository.existsById(id)
    }

    override fun findDetailViewList(ids: List<Long>): List<CourseDetailView> {
        return this.courseJpaRepository.findDetailViewList(ids)
    }

    override fun deleteById(id: Long) {
        this.courseJpaRepository.deleteById(id)
    }

    private fun toEntity(course: Course): CourseEntity {
        return if (course.id != 0L) {
            this.courseJpaRepository.findById(course.id)
                .orElseThrow { NotFoundException(ErrorCode.COURSE_NOT_FOUND) }
                .apply { update(course) }
        } else {
            CourseEntity.from(course)
        }
    }
}