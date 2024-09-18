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
    var maxEnrollment: Int = 0,

    @Column(nullable = false, columnDefinition = "integer")
    var addressId: Long = 0,

    @OneToOne
    @JoinColumn(name = "course_id")
    val courseEntity: CourseEntity,

    ) : AuditEntity() {
    fun update(offlineInfo: OfflineCourseInfo) {
        this.maxEnrollment = offlineInfo.maxEnrollment
        this.addressId = offlineInfo.addressId
    }
}