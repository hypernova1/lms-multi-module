package org.sam.lms.persistence.review

import jakarta.persistence.*
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.*
import org.sam.lms.domain.review.domain.Review
import org.sam.lms.domain.review.domain.Score
import org.sam.lms.persistence.AuditEntity

@FilterDef(name = "deletedReviewFilter", parameters = [ParamDef(name = "deletedDate", type = Boolean::class)])
@Filter(name = "deletedReviewFilter", condition = "deleted_date IS NULL")
@SQLDelete(sql = "UPDATE review SET deleted_date = current_timestamp WHERE id = ?")
@SQLRestriction("deleted_date is null")
@Table(
    name = "review",
    indexes = [Index(name = "review_course_id_idx", columnList = "course_id"), Index(
        name = "review_course_id_idx",
        columnList = "account_id"
    )]
)
@Entity
class ReviewEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Comment("내용")
    @Column(nullable = false, columnDefinition = "varchar")
    var content: String,

    @Comment("점수")
    @Column()
    var score: Int,

    @Comment("강의 아이디")
    @Column
    val courseId: Long,

    @Comment("등록자 아이디")
    @Column
    val accountId: Long,
) : AuditEntity() {
    fun toDomain(): Review {
        return Review(
            id = this.id,
            content = this.content,
            score = Score.create(this.score),
            courseId = this.courseId,
            studentId = this.accountId
        )
    }
}