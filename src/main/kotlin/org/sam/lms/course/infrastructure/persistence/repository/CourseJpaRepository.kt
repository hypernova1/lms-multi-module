package org.sam.lms.course.infrastructure.persistence.repository

import jakarta.persistence.LockModeType
import org.sam.lms.course.application.payload.out.CourseDetailView
import org.sam.lms.course.application.payload.out.CourseSummaryView
import org.sam.lms.course.domain.CourseRepository
import org.sam.lms.course.domain.CourseStatus
import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CourseJpaRepository : JpaRepository<CourseEntity, Long>, CourseRepository {

    @Query("SELECT course, category, offlineCourse " +
            "FROM CourseEntity course " +
            "JOIN FETCH course.courseCategories courseCategory " +
            "JOIN FETCH courseCategory.categoryEntity category " +
            "LEFT JOIN FETCH course.offlineCourseEntity offlineCourse " +
            "WHERE course.id = :id")
    override fun findById(id: Long): Optional<CourseEntity>

    override fun deleteById(id: Long)

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT course, category, offlineCourse " +
            "FROM CourseEntity course " +
            "JOIN FETCH course.courseCategories courseCategory " +
            "JOIN FETCH courseCategory.categoryEntity category " +
            "LEFT JOIN FETCH course.offlineCourseEntity offlineCourse " +
            "WHERE course.id IN (:ids)")
    override fun findByIdsWithPessimisticLock(ids: List<Long>): List<CourseEntity>

    @Query("""
        SELECT new org.sam.lms.course.application.payload.out.CourseSummaryView(course.id, course.title, course.price, offlineCourse.maxEnrollment, account.name)
        FROM CourseEntity course
        JOIN course.offlineCourseEntity offlineCourse
        JOIN AccountEntity account ON account.id = course.accountId
    """)
    override fun findSummaryView(status: CourseStatus, pageable: Pageable): Page<CourseSummaryView>

    @Query("""
        SELECT new org.sam.lms.course.application.payload.out.CourseDetailView(course.id, course.title, course.description, course.price, category.id, category.name, account.id, account.name, offlineCourse.maxEnrollment)
        FROM CourseEntity course
        JOIN course.offlineCourseEntity offlineCourse
        JOIN course.courseCategories courseCategory
        JOIN courseCategory.categoryEntity category
        JOIN AccountEntity account ON account.id = course.accountId
        WHERE course.id = :id
    """)
    override fun findDetailView(id: Long): Optional<CourseDetailView>

}