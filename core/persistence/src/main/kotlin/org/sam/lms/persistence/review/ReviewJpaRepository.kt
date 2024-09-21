package org.sam.lms.persistence.review

import org.springframework.data.jpa.repository.JpaRepository

interface ReviewJpaRepository : JpaRepository<ReviewEntity, Long> {
}