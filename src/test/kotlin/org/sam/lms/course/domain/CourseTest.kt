package org.sam.lms.course.domain

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CourseTest {

    @Test
    @DisplayName("강의를 수정한다")
    fun update_course() {
        val course = Course(
            id = 1,
            title = "강의 제목",
            description = "강의 설명",
            category = Category(1, "카테고리 제목"),
            price = 1_000,
            teacherId = 1,
            type = CourseType.ONLINE
        )
    }

    @Test
    @DisplayName("강의를 오픈한다")
    fun update() {

    }

}