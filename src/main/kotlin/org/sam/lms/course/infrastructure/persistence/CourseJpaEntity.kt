package org.sam.lms.course.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.sam.lms.infra.persistence.AuditEntity

@Table(name = "course")
@Entity
class CourseJpaEntity(
    @Id
    val id: Long,
    @Column(nullable = false, columnDefinition = "varchar")
    var title: String,
    @Column(nullable = false, columnDefinition = "text")
    var description: String,
    @Column(nullable = false, columnDefinition = "integer")
    var numberOfStudents: Int = 0,
    @Column(nullable = false, columnDefinition = "tinyint")
    var visible: Boolean = false
) : AuditEntity() {
}