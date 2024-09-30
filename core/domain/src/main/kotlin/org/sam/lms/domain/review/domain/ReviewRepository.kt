package org.sam.lms.domain.review.domain

interface ReviewRepository {
    fun save(review: Review): Review
}