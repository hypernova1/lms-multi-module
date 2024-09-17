package org.sam.lms.course.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.junit.jupiter.MockitoExtension
import org.sam.lms.address.application.AddressService
import org.sam.lms.common.exception.ForbiddenException
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.`in`.UpdateCourseDto
import org.sam.lms.course.domain.*

@ExtendWith(MockitoExtension::class)
class CourseServiceTest {

    private lateinit var courseService: CourseService

    @Mock
    private lateinit var addressService: AddressService

    @Mock
    private lateinit var courseRepository: CourseRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var courseTicketReader: CourseTicketReader

    @Mock
    private lateinit var courseTicketWriter: CourseTicketWriter

    @BeforeEach
    fun init() {
        this.courseService =
            CourseService(
                courseRepository,
                categoryRepository,
                courseTicketReader,
                courseTicketWriter,
                addressService
            )
    }

    @Test
    @DisplayName("강의를 등록한다")
    fun create_course() {
        //given
        val dto = CreateCourseDto(title = "테스트 강의", description = "테스트 강의 설명입니다.", categoryId = 1, price = 1_000)

        `when`(categoryRepository.findById(1L)).thenReturn(Category(1L, "테스트 카테고리"))

        //when
        val categorySummary = courseService.create(dto, 1)

        //then
        assertEquals(categorySummary.title, dto.title)
    }

    @Test
    @DisplayName("강의를 수정한다.")
    fun update_course() {
        //given
        val preTitle = "테스트 강의1"
        val dto =
            UpdateCourseDto(id = 1L, title = "테스트 강의2", description = "테스트 강의2 설명입니다.", categoryId = 2L, price = 1_000)
        val course =
            Course(
                1L,
                preTitle,
                description = "테스트 강의 1 설명입니다.",
                category = Category(2L, "카테고리 2"),
                teacherId = 1,
                type = CourseType.ONLINE
            )


        `when`(categoryRepository.findById(2L)).thenReturn(Category(2L, "카테고리 2"))
        `when`(courseRepository.findById(1L)).thenReturn(course)

        //when
        val categorySummary = courseService.update(dto, 1)

        //then
        assertNotEquals(preTitle, categorySummary.title)
    }

    @Test
    @DisplayName("강의를 수정한다 (수정하려는 강사가 다를 경우)")
    fun update_course_forbidden() {
        //given
        val preTitle = "테스트 강의1"
        val dto =
            UpdateCourseDto(id = 1L, title = "테스트 강의2", description = "테스트 강의2 설명입니다.", categoryId = 2L, price = 1_000)
        val course =
            Course(
                1L,
                preTitle,
                description = "테스트 강의 1 설명입니다.",
                category = Category(2L, "카테고리 2"),
                teacherId = 1,
                type = CourseType.ONLINE
            )


        `when`(categoryRepository.findById(2L)).thenReturn(Category(2L, "카테고리 2"))
        `when`(courseRepository.findById(1L)).thenReturn(course)

        //when, then
        assertThrows<ForbiddenException> { courseService.update(dto, 2) }
    }

    @Test
    @DisplayName("수강 신청을 한다.")
    fun enroll_test() {
        //given
        val course = Course(
            id = 1L,
            title = "테스트 강의",
            description = "",
            numberOfStudents = 0,
            category = Category(1L, "테스트 카테고리"),
            price = 0,
            teacherId = 1L,
            type = CourseType.ONLINE
        )
        val studentId = 1L
        val courseTicket = CourseTicket(
            courseId = 1L,
            studentId = 1L,
        )

        `when`(courseRepository.findById(course.id)).thenReturn(course)
        doNothing().`when`(courseTicketReader).checkAlreadyEnrolled(courseId = course.id, studentId = studentId)
        `when`(courseTicketWriter.save(any())).thenReturn(courseTicket)
        `when`(courseRepository.save(any())).thenReturn(course)

        // when
        val result = courseService.enroll(course.id, studentId)

        // then
        assertEquals(courseTicket.id, result.id)
        assertEquals(course.numberOfStudents, 1)
        assertThat(courseTicket.applicationDate).isNotNull()
    }

}