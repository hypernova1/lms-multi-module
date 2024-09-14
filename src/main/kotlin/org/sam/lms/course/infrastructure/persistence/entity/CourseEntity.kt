package org.sam.lms.course.infrastructure.persistence.entity

import jakarta.persistence.*
import org.sam.lms.course.domain.*
import org.sam.lms.infra.persistence.AuditEntity

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
    var numberOfStudents: Int = 0,

    @Column(nullable = false, columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    var status: CourseStatus = CourseStatus.HIDDEN,

    @Column(nullable = false, columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    var type: CourseType = CourseType.ONLINE,

    @Column(nullable = false, columnDefinition = "integer")
    val accountId: Long,

    @OneToOne(mappedBy = "courseEntity", cascade = [CascadeType.ALL])
    var offlineCourseEntity: OfflineCourseEntity? = null,

    @OneToMany(mappedBy = "courseEntity", cascade = [CascadeType.ALL])
    val courseCategories: MutableList<CourseCategoryEntity> = mutableListOf()
) : AuditEntity() {
    fun toDomain(): Course {
        val course = Course(
            id = this.id,
            title = this.title,
            description = this.description,
            numberOfStudents = this.numberOfStudents,
            category = Category(
                this.courseCategories[0].categoryEntity.id,
                this.courseCategories[0].categoryEntity.name
            ),
            status = this.status,
            teacherId = this.accountId,
            type = this.type,
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
        this.title = course.title
        this.description = course.description
        this.numberOfStudents = course.numberOfStudents
        this.status = course.status
        this.courseCategories.first().id.categoryId = categoryEntity.id
        this.type = course.type

        if (course.offlineInfo == null) {
            return
        }

        if (this.offlineCourseEntity == null) {
            this.offlineCourseEntity = OfflineCourseEntity(
                maxEnrollment = course.offlineInfo!!.maxEnrollment,
                addressId = course.offlineInfo!!.addressId,
                courseEntity = this
            )
        } else {
            this.offlineCourseEntity!!.update(course.offlineInfo!!)
        }
    }

    companion object {
        fun from(course: Course): CourseEntity {
            val courseEntity =
                CourseEntity(title = course.title, description = course.description, accountId = course.teacherId)
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