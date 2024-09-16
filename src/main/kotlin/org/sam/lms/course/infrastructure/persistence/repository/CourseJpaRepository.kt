package org.sam.lms.course.infrastructure.persistence.repository

import jakarta.persistence.LockModeType
import org.sam.lms.course.domain.CourseRepository
import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity
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

}