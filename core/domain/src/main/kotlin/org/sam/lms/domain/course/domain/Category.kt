package org.sam.lms.domain.course.domain

import java.io.Serializable

data class Category(val id: Long = 0, val name: String) : Serializable {
    companion object {
        fun from(name: String): Category {
            return Category(name = name)
        }
    }
}