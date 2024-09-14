package org.sam.lms.course.domain

import org.sam.lms.course.infrastructure.persistence.entity.CourseCategoryEntity

interface CourseCategoryRepository {
    fun save(courseCategoryEntity: CourseCategoryEntity): CourseCategoryEntity
}