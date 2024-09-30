package org.sam.lms.persistence.course.entity

import jakarta.persistence.*
import org.hibernate.annotations.Filter
import org.hibernate.annotations.SQLDelete
import org.sam.lms.domain.course.domain.Course
import org.sam.lms.domain.course.domain.CourseType
import org.sam.lms.domain.course.domain.OfflineCourseInfo
import org.sam.lms.persistence.AuditEntity

@Filter(name = "deletedAccountFilter", condition = "deleted_date IS NULL OR :deletedDate = true")
@SQLDelete(sql = "UPDATE course SET deleted_date = current_timestamp WHERE id = ?")
@Table(name = "course")
@Entity
class CourseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, columnDefinition = "varchar")
    var title: String,

    @Column(nullable = false, columnDefinition = "text")
    var description: String,

    @Column(nullable = false, columnDefinition = "integer")
    var price: Int,

    @Column(nullable = false, columnDefinition = "integer")
    var numberOfStudents: Int = 0,

    @Column(nullable = false, columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    var status: org.sam.lms.domain.course.domain.CourseStatus = org.sam.lms.domain.course.domain.CourseStatus.HIDDEN,

    @Column(nullable = false, columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    var type: CourseType = CourseType.ONLINE,

    @Column(nullable = false, columnDefinition = "bigint")
    val accountId: Long,

    @OneToOne(mappedBy = "courseEntity", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var offlineCourseEntity: OfflineCourseEntity? = null,

    @Column(nullable = false, columnDefinition = "bigint")
    var categoryId: Long,
) : AuditEntity() {
    fun toDomain(): Course {
        val course = Course(
            id = this.id,
            title = this.title,
            type = this.type,
            status = this.status,
            description = this.description,
            numberOfStudents = this.numberOfStudents,
            categoryId = this.categoryId,
            teacherId = this.accountId,
        )

        if (this.offlineCourseEntity != null) {
            course.offlineInfo = OfflineCourseInfo(
                id = this.offlineCourseEntity!!.id,
                maxEnrollments = this.offlineCourseEntity!!.maxEnrollments,
                addressId = this.offlineCourseEntity!!.addressId
            )
        }

        return course
    }

    fun update(course: Course) {
        this.status = course.status
        this.type = course.type
        this.title = course.title
        this.price = course.price
        this.description = course.description
        this.numberOfStudents = course.numberOfStudents;
        this.categoryId = course.categoryId

        if (course.offlineInfo == null) {
            return
        }

        if (this.offlineCourseEntity == null) {
            this.offlineCourseEntity = OfflineCourseEntity(
                maxEnrollments = course.offlineInfo!!.maxEnrollments,
                addressId = course.offlineInfo!!.addressId,
                courseEntity = this
            )
            return
        }

        this.offlineCourseEntity!!.update(course.offlineInfo!!)
    }

    companion object {
        fun from(course: Course): CourseEntity {
            val courseEntity =
                CourseEntity(
                    id = course.id,
                    type = course.type,
                    title = course.title,
                    description = course.description,
                    price = course.price,
                    status = course.status,
                    categoryId = course.categoryId,
                    accountId = course.teacherId,
                )
            if (course.offlineInfo != null) {
                courseEntity.offlineCourseEntity = OfflineCourseEntity(
                    maxEnrollments = course.offlineInfo!!.maxEnrollments,
                    addressId = course.offlineInfo!!.addressId,
                    courseEntity = courseEntity
                )
            }
            return courseEntity
        }
    }
}