package org.sam.lms.course.application

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.sam.lms.common.exception.BadRequestException
import org.sam.lms.course.domain.*
import org.sam.lms.course.infrastructure.persistence.entity.CategoryEntity
import org.sam.lms.course.infrastructure.persistence.repository.CategoryJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger


@SpringBootTest
class EnrollmentDistributeTest {

    @Autowired
    private lateinit var courseReader: CourseReader

    @Autowired
    private lateinit var categoryRepository: CategoryJpaRepository

    @Autowired
    private lateinit var courseWriter: CourseWriter

    @Autowired
    private lateinit var courseService: CourseService

    @Autowired
    private lateinit var courseTicketReader: CourseTicketReader


    @Test
    @DisplayName("분산락 테스트")
    fun test() {
        val categoryEntity = this.categoryRepository.save(CategoryEntity(name = "테스트 카테고리"))

        val course = Course(
            title = "테스트 강의",
            description = "",
            numberOfStudents = 0,
            price = 0,
            teacherId = 1L,
            type = CourseType.OFFLINE,
            category = Category(id = categoryEntity.id, name = "테스트 카테고리"),
            offlineInfo = OfflineCourseInfo(maxEnrollment = 10)
        )

        courseWriter.save(course)

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
        assertEquals(90, numberOfThrows.get())
    }

}