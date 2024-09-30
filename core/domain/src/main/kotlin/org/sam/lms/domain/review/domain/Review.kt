package org.sam.lms.domain.review.domain

import org.sam.lms.domain.account.domain.Provider
import org.sam.lms.domain.review.application.`in`.CreateReviewDto

data class Review(
    val id: Long = 0L,
    val studentId: Long,
    val content: String,
    val score: Score,
    val courseId: Long,
) {
    companion object {
        fun of(reviewRequest: CreateReviewDto, provider: Provider, courseId: Long): Review {
            return Review(
                studentId = provider.id,
                content = reviewRequest.content,
                score = Score.create(reviewRequest.score),
                courseId = courseId
            )
        }
    }
}