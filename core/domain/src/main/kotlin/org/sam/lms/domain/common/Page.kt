package org.sam.lms.domain.common

open class Page<T>(
    val page: Int,
    val size: Int,
    val totalPage: Int,
    val items: List<T>
)