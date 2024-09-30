package org.sam.lms.domain.review.application

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.domain.account.domain.Provider
import org.sam.lms.domain.course.application.CourseService
import org.sam.lms.domain.review.application.`in`.CreateReviewDto
import org.sam.lms.domain.review.domain.Review
import org.sam.lms.domain.review.domain.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(private val courseService: CourseService, private val reviewRepository: ReviewRepository) {

    /**
     * 수강평을 등록한다.
     *
     * @param courseId 강의 아이디
     * @param provider 유저
     * @param createReviewDto 리뷰 내용
     * @return 리뷰 아이디
     * */
    fun registerReview(courseId: Long, provider: Provider, createReviewDto: CreateReviewDto): Long {
        val existsCourse = this.courseService.existsById(courseId)
        if (!existsCourse) {
            throw NotFoundException(ErrorCode.COURSE_NOT_FOUND)
        }

        val review = Review.of(courseId = courseId, reviewRequest = createReviewDto, provider = provider)
        val savedReview = reviewRepository.save(review)
        return savedReview.id
    }
}