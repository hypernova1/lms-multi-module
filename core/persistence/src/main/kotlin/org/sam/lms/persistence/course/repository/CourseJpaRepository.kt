package org.sam.lms.persistence.course.repository

import jakarta.persistence.LockModeType
import org.sam.lms.domain.course.application.payload.out.CourseDetailView
import org.sam.lms.domain.course.application.payload.out.CourseSummaryView
import org.sam.lms.persistence.course.entity.CourseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CourseJpaRepository : JpaRepository<CourseEntity, Long> {

    @Query("""
        SELECT course, category, offlineCourse 
        FROM CourseEntity course 
        JOIN FETCH CategoryEntity category ON course.categoryId = category.id
        LEFT JOIN FETCH course.offlineCourseEntity offlineCourse 
        WHERE course.id = :id
        """
    )
    override fun findById(id: Long): Optional<CourseEntity>

    override fun deleteById(id: Long)

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("""
        SELECT course, category, offlineCourse
        FROM CourseEntity course
        JOIN FETCH CategoryEntity category ON course.categoryId = category.id 
        LEFT JOIN FETCH course.offlineCourseEntity offlineCourse 
        WHERE course.id IN (:ids)
        """
    )
    fun findByIdsWithPessimisticLock(ids: List<Long>): List<CourseEntity>

    @Query(
        """
        SELECT new org.sam.lms.domain.course.application.payload.out.CourseSummaryView(course.id, course.title, course.price, offlineCourse.maxEnrollments, account.name)
        FROM CourseEntity course
        JOIN course.offlineCourseEntity offlineCourse
        JOIN AccountEntity account ON account.id = course.accountId
    """
    )
    fun findSummaryView(status: org.sam.lms.domain.course.domain.CourseStatus, pageable: Pageable): Page<CourseSummaryView>

    @Query(
        """
        SELECT new org.sam.lms.domain.course.application.payload.out.CourseDetailView(course.id, course.title, course.description, course.price, category.id, category.name, account.id, account.name, offlineCourse.maxEnrollments, course.numberOfStudents)
        FROM CourseEntity course
        JOIN course.offlineCourseEntity offlineCourse
        JOIN CategoryEntity category ON course.categoryId = category.id
        JOIN AccountEntity account ON account.id = course.accountId
        WHERE course.id = :id
    """
    )
    fun findDetailView(id: Long): Optional<CourseDetailView>

}