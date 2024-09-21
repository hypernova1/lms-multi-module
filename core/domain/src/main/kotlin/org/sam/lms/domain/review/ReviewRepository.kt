package org.sam.lms.domain.review

interface ReviewRepository {
    fun save(review: Review): Review
}