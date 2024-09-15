package org.sam.lms.course.infrastructure.persistence.entity

import jakarta.persistence.*
import org.hibernate.annotations.Filter
import org.hibernate.annotations.SQLDelete
import org.sam.lms.course.domain.*
import org.sam.lms.infra.persistence.AuditEntity

@Filter(name = "deletedAccountFilter", condition = "deleted_date IS NULL OR :deletedDate = true")
@SQLDelete(sql = "UPDATE course SET deleted_date = current_timestamp WHERE id = ?")
@Table(name = "course")
@Entity
class CourseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false, columnDefinition = "varchar")
    var title: String,

    @Column(nullable = false, columnDefinition = "text")
    var description: String,

    @Column(nullable = false, columnDefinition = "integer")
    var numberOfStudents: Int = 0,

    @Column(nullable = false, columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    var status: CourseStatus = CourseStatus.HIDDEN,

    @Column(nullable = false, columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    var type: CourseType = CourseType.ONLINE,

    @Column(nullable = false, columnDefinition = "integer")
    val accountId: Long,

    @OneToOne(mappedBy = "courseEntity", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var offlineCourseEntity: OfflineCourseEntity? = null,

    @OneToMany(mappedBy = "courseEntity", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val courseCategories: MutableList<CourseCategoryEntity> = mutableListOf()
) : AuditEntity() {
    fun toDomain(): Course {
        val course = Course(
            id = this.id,
            title = this.title,
            type = this.type,
            status = this.status,
            description = this.description,
            numberOfStudents = this.numberOfStudents,
            category = Category(
                this.courseCategories[0].categoryEntity.id,
                this.courseCategories[0].categoryEntity.name
            ),
            teacherId = this.accountId,
        )

        if (this.offlineCourseEntity != null) {
            course.offlineInfo = OfflineCourseInfo(
                id = this.offlineCourseEntity!!.id,
                maxEnrollment = this.offlineCourseEntity!!.maxEnrollment,
                addressId = this.offlineCourseEntity!!.addressId
            )
        }

        return course
    }

    fun update(course: Course, categoryEntity: CategoryEntity) {
        this.id = course.id
        this.status = course.status
        this.type = course.type
        this.title = course.title
        this.description = course.description
        this.numberOfStudents = course.numberOfStudents
        this.courseCategories.first().id.categoryId = categoryEntity.id

        if (course.offlineInfo == null) {
            return
        }

        if (this.offlineCourseEntity == null) {
            this.offlineCourseEntity = OfflineCourseEntity(
                maxEnrollment = course.offlineInfo!!.maxEnrollment,
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
                    status = course.status,
                    accountId = course.teacherId,
                )
            if (course.offlineInfo != null) {
                courseEntity.offlineCourseEntity = OfflineCourseEntity(
                    maxEnrollment = course.offlineInfo!!.maxEnrollment,
                    addressId = course.offlineInfo!!.addressId,
                    courseEntity = courseEntity
                )
            }
            return courseEntity
        }
    }
}