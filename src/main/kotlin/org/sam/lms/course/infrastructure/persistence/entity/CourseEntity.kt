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

    @Column(nullable = false, columnDefinition = "boolean")
    var visible: Boolean = false,

    @Column(nullable = false, columnDefinition = "integer")
    val accountId: Long,

    @OneToMany(mappedBy = "courseEntity", cascade = [CascadeType.ALL])
    val courseCategories: MutableList<CourseCategoryEntity> = mutableListOf()
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
            visible = this.visible,
            teacherId = this.accountId
        )
    }

    fun update(course: Course, categoryEntity: CategoryEntity) {
        this.title = course.title
        this.description = course.description
        this.numberOfStudents = course.numberOfStudents
        this.visible = course.visible
        this.courseCategories.first().id.categoryId = categoryEntity.id
    }
}