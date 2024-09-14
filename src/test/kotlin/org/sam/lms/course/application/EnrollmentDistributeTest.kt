package org.sam.lms.course.application

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.sam.lms.common.exception.BadRequestException
import org.sam.lms.course.domain.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max


@SpringBootTest
class EnrollmentDistributeTest {

    @Autowired
    private lateinit var courseReader: CourseReader

    @Autowired
    private lateinit var courseProcessor: CourseProcessor

    @Autowired
    private lateinit var courseService: CourseService

    @Autowired
    private lateinit var courseTicketReader: CourseTicketReader

    @Test
    @DisplayName("분산락 테스트")
    fun test() {
        val course = Course(
            id = 1L,
            title = "테스트 강의",
            description = "",
            numberOfStudents = 0,
            category = Category(1L, "테스트 카테고리"),
            price = 0,
            teacherId = 1L,
            type = CourseType.OFFLINE
        )

        course.offlineInfo = OfflineCourseInfo(maxEnrollment = 10)

        courseProcessor.save(course)

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

        assertEquals(10, numberOfTickets)
        assertEquals(90, numberOfThrows)
    }

}