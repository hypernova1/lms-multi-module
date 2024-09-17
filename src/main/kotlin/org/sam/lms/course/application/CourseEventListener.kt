package org.sam.lms.course.application

import org.sam.lms.account.application.AccountDeleteEvent
import org.sam.lms.course.domain.*
import org.springframework.context.event.EventListener
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class CourseEventListener(
    private val courseRepository: CourseRepository,
    private val courseTicketReader: CourseTicketReader,
    private val courseTicketWriter: CourseTicketWriter,
    private val courseService: CourseService,
) {

    /**
     * 해당 학생의 강의 티켓을 삭제하고 해당 강의의 인원수를 감소시킨다.
     * - 비관적 락을 사용해서 동시성 방지
     * */
    @EventListener
    @Async
    @Retryable(maxAttempts = 3)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun deleteCourseTickets(accountDeleteEvent: AccountDeleteEvent) {
        val courseTickets = this.courseTicketReader.findByStudentId(accountDeleteEvent.studentId)
        val courseIds = courseTickets.map { it.courseId }
        val courses = courseRepository.findByIdsWithPessimisticLock(courseIds)
        courses.forEach { it.decreaseNumberOfStudents() }
        this.courseRepository.saveAll(courses)
        this.courseTicketWriter.deleteAll(courseTickets.map { it.id })
    }

}