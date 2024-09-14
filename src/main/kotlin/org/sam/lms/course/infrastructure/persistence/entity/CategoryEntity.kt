package org.sam.lms.course.infrastructure.persistence.entity

import jakarta.persistence.*
import org.sam.lms.course.domain.Category
import org.sam.lms.infra.persistence.AuditEntity

@Table(name = "category")
@Entity
class CategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name", nullable = false)
    val name: String,
) : AuditEntity() {

    fun toDomain(): Category {
        return Category(id, name)
    }
}