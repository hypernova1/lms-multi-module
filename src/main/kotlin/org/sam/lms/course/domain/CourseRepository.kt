package org.sam.lms.course.domain

import jakarta.persistence.LockModeType
import org.sam.lms.course.infrastructure.persistence.entity.CourseEntity
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CourseRepository {
    fun save(courseEntity: CourseEntity): CourseEntity
    fun findById(id: Long): Optional<CourseEntity>
    fun deleteById(id: Long)
    fun findByIdsWithPessimisticLock(ids: List<Long>): List<CourseEntity>
}