package org.sam.lms.persistence.course.entity

import jakarta.persistence.*
import jakarta.persistence.Table
import org.hibernate.annotations.*
import org.sam.lms.domain.course.domain.OfflineCourseInfo
import org.sam.lms.persistence.AuditEntity

@FilterDef(name = "deletedOfflineCourseFilter", parameters = [ParamDef(name = "deletedDate", type = Boolean::class)])
@Filter(name = "deletedOfflineCourseFilter", condition = "deleted_date IS NULL")
@SQLDelete(sql = "UPDATE offline_course SET deleted_date = current_timestamp WHERE id = ?")
@SQLRestriction("deleted_date is null")
@Table(name = "offline_course")
@Entity
class OfflineCourseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, columnDefinition = "integer")
    var maxEnrollments: Int = 0,

    @Column(nullable = false, columnDefinition = "bigint")
    var addressId: Long = 0,

    @OneToOne
    @JoinColumn(name = "course_id")
    val courseEntity: CourseEntity,

    ) : AuditEntity() {
    fun update(offlineInfo: OfflineCourseInfo) {
        this.maxEnrollments = offlineInfo.maxEnrollments
        this.addressId = offlineInfo.addressId
    }
}