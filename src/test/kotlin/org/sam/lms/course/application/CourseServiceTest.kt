package org.sam.lms.course.application

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.sam.lms.common.exception.ForbiddenException
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.`in`.UpdateCourseDto
import org.sam.lms.course.domain.*

@ExtendWith(MockitoExtension::class)
class CourseServiceTest {

    private lateinit var courseService: CourseService

    @Mock
    private lateinit var courseReader: CourseReader

    @Mock
    private lateinit var courseProcessor: CourseProcessor

    @Mock
    private lateinit var categoryReader: CategoryReader

    @Mock
    private lateinit var courseTicketReader: CourseTicketReader

    @Mock
    private lateinit var courseTicketProcessor: CourseTicketProcessor

    @BeforeEach
    fun init() {
        this.courseService =
            CourseService(courseReader, courseProcessor, categoryReader, courseTicketReader, courseTicketProcessor)
    }

    @Test
    @DisplayName("강의를 등록한다")
    fun create_course() {
        val dto = CreateCourseDto(title = "테스트 강의", description = "테스트 강의 설명입니다.", categoryId = 1, price = 1_000)

        `when`(categoryReader.findOne(1L)).thenReturn(Category(1L, "테스트 카테고리"))

        val categorySummary = courseService.create(dto, 1)

        assertEquals(categorySummary.title, dto.title)
    }

    @Test
    @DisplayName("강의를 수정한다.")
    fun update_course() {
        val preTitle = "테스트 강의1"
        val dto =
            UpdateCourseDto(id = 1L, title = "테스트 강의2", description = "테스트 강의2 설명입니다.", categoryId = 2L, price = 1_000)
        val course =
            Course(1L, preTitle, description = "테스트 강의 1 설명입니다.", category = Category(2L, "카테고리 2"), teacherId = 1)


        `when`(categoryReader.findOne(2L)).thenReturn(Category(2L, "카테고리 2"))
        `when`(courseReader.findOne(1L)).thenReturn(course)

        val categorySummary = courseService.update(dto, 1)

        assertNotEquals(preTitle, categorySummary.title)
    }

    @Test
    @DisplayName("강의를 수정한다 (수정하려는 강사가 다를 경우)")
    fun update_course_forbidden() {
        val preTitle = "테스트 강의1"
        val dto =
            UpdateCourseDto(id = 1L, title = "테스트 강의2", description = "테스트 강의2 설명입니다.", categoryId = 2L, price = 1_000)
        val course =
            Course(1L, preTitle, description = "테스트 강의 1 설명입니다.", category = Category(2L, "카테고리 2"), teacherId = 1)


        `when`(categoryReader.findOne(2L)).thenReturn(Category(2L, "카테고리 2"))
        `when`(courseReader.findOne(1L)).thenReturn(course)

        assertThrows<ForbiddenException> { courseService.update(dto, 2) }
    }

}