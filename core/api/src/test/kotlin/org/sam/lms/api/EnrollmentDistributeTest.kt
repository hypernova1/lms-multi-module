package org.sam.lms.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.sam.lms.common.exception.BadRequestException
import org.sam.lms.domain.address.application.AddressService
import org.sam.lms.domain.course.application.CategoryService
import org.sam.lms.domain.course.application.CourseService
import org.sam.lms.domain.course.domain.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger


@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension::class)
class EnrollmentDistributeTest(
    @Autowired
    val categoryService: CategoryService,
    @Autowired
    val courseRepository: CourseRepository,
    @Autowired
    val courseTicketReader: CourseTicketReader,
    @Autowired
    val courseTicketWriter: CourseTicketWriter,
    @Autowired
    private val addressService: AddressService,

    @Autowired
    private val courseService: CourseService
) {

    @BeforeEach
    fun setUp() {
//        this.courseService =
//            CourseService(courseRepository, categoryService, courseTicketReader, courseTicketWriter, addressService)
    }

    @Test
    @DisplayName("분산락 테스트")
    fun test() {
        val course = courseRepository.save(Course(
            title = "테스트 강의",
            description = "",
            numberOfStudents = 0,
            price = 0,
            teacherId = 1L,
            type = CourseType.OFFLINE,
            categoryId = 1L,
            offlineInfo = OfflineCourseInfo(maxEnrollments = 10)
        ))

//        `when`(categoryService.existsById(1L)).thenReturn(true)

        val numberOfThreads = 100L
        val executorService = Executors.newFixedThreadPool(numberOfThreads.toInt())
        val latch = CountDownLatch(numberOfThreads.toInt())
        val numberOfThrows = AtomicInteger(0)

        for (i in 0 until numberOfThreads) {
            executorService.submit {
                try {
                    courseService.enroll(course.id, i)
                } catch (e: BadRequestException) {
                    numberOfThrows.incrementAndGet()
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        val numberOfTickets = courseTicketReader.countByCourseId(course.id)

        assertEquals(numberOfTickets, 10)
        assertEquals(90, numberOfThrows.get())
    }

}