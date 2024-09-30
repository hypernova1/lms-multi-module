package org.sam.lms.store.domain.order.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object OrderNoCreator {

    private val datetimeFormatter = DateTimeFormatter.ofPattern("YYYYMMDDssSSS")

    fun create(): String {
        return LocalDateTime.now().format(datetimeFormatter)
    }
}