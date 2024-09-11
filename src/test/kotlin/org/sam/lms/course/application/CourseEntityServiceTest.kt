package org.sam.lms.course.application

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.sam.lms.account.domain.Account
import org.sam.lms.account.domain.RoleName
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.domain.Category
import org.sam.lms.course.domain.CategoryReader
import org.sam.lms.course.domain.CourseProcessor
import org.sam.lms.course.domain.CourseReader
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class CourseEntityServiceTest {

    private lateinit var courseService: CourseService

    @Mock
    private lateinit var courseReader: CourseReader
    @Mock
    private lateinit var courseProcessor: CourseProcessor
    @Mock
    private lateinit var categoryReader: CategoryReader

    @BeforeEach
    fun init() {
        this.courseService = CourseService(courseReader, courseProcessor, categoryReader)
    }

    @Test
    @DisplayName("강의를 등록한다")
    fun create_course() {
        val dto = CreateCourseDto(title = "테스트 강의", "테스트 강의 설명입니다.", 1, 1_000)
        val account = Account(1L, "test@test.com", "sam", RoleName.ROLE_TEACHER, "1111", LocalDateTime.now())

        `when`(categoryReader.findById(1L)).thenReturn(Category(1L, "테스트 카테고리"))

        val categorySummary = courseService.create(dto, account)

        assertEquals(categorySummary.title, dto.title)
    }

}