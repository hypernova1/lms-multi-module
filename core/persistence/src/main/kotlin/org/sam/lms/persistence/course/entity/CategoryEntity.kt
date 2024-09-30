package org.sam.lms.persistence.course.entity

import jakarta.persistence.*
import org.hibernate.annotations.Filter
import org.hibernate.annotations.SQLDelete
import org.sam.lms.domain.course.domain.Category
import org.sam.lms.persistence.AuditEntity

@Filter(name = "deletedAccountFilter", condition = "deleted_date IS NULL OR :deletedDate = true")
@SQLDelete(sql = "UPDATE category SET deleted_date = current_timestamp WHERE id = ?")
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

    companion object {
        fun from(category: Category): CategoryEntity {
            return CategoryEntity(id = category.id, name = category.name)
        }
    }
}