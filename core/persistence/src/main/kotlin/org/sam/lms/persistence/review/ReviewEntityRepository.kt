package org.sam.lms.persistence.review

import org.sam.lms.domain.review.Review
import org.sam.lms.domain.review.ReviewRepository
import org.springframework.stereotype.Repository

@Repository
class ReviewEntityRepository(private val reviewJpaRepository: ReviewJpaRepository) : ReviewRepository {
    override fun save(review: Review): Review {
        val reviewEntity = reviewJpaRepository.save(toEntity(review))
        return review.copy(id = reviewEntity.id)
    }

    fun toEntity(review: Review): ReviewEntity {
        return ReviewEntity(
            review.id,
            content = review.content,
            score = review.score.value,
            courseId = review.courseId,
            accountId = review.studentId
        )
    }
}