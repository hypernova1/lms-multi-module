package org.sam.lms.persistence.course.entity

import jakarta.persistence.*
import org.sam.lms.domain.course.domain.OfflineCourseInfo
import org.sam.lms.persistence.AuditEntity

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