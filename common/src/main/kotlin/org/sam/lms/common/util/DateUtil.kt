package org.sam.lms.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtil {

    private val format = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")

    fun toString(date: LocalDateTime): String {
        return date.format(format)
    }

}