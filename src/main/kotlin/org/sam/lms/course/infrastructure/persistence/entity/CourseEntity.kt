package org.sam.lms.course.infrastructure.persistence.entity

import jakarta.persistence.*
import org.sam.lms.course.domain.Category
import org.sam.lms.course.domain.Course
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
    @Column(nullable = false, columnDefinition = "tinyint")
    var visible: Boolean = false,
    @Column(nullable = false, columnDefinition = "integer")
    val accountId: Long,
    @OneToMany
    val courseCategories: List<CourseCategoryEntity> = listOf()
) : AuditEntity() {
    fun toDomain(): Course {
        return Course(
            id = this.id,
            title = this.title,
            description = this.description,
            numberOfStudents = this.numberOfStudents,
            category = Category(
                this.courseCategories[0].categoryEntity.id,
                this.courseCategories[0].categoryEntity.name
            ),
            teacherId = this.accountId
        )
    }
}