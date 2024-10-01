package org.sam.lms.domain.course.application

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.common.util.DateUtil
import org.sam.lms.domain.address.application.AddressService
import org.sam.lms.domain.common.Page
import org.sam.lms.domain.common.Paging
import org.sam.lms.domain.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.domain.course.application.payload.`in`.UpdateCourseDto
import org.sam.lms.domain.course.application.payload.out.CourseDetailView
import org.sam.lms.domain.course.application.payload.out.CourseSummaryView
import org.sam.lms.domain.course.application.payload.out.CourseTicketSummary
import org.sam.lms.domain.course.domain.*
import org.sam.lms.infrastructure.lock.DistributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val categoryService: CategoryService,
    private val courseTicketReader: CourseTicketReader,
    private val courseTicketWriter: CourseTicketWriter,
    private val addressService: AddressService,
) {

    /**
     * 강의 정보를 등록한다.
     *
     * @param createCourseDto 강의 정보
     * @param accountId 등록자 아이디
     * @return 강의 요약 정보
     * */
    @Transactional
    fun create(createCourseDto: CreateCourseDto, accountId: Long): Course {
        val existsCategory = this.categoryService.existsById(createCourseDto.categoryId)
        if (!existsCategory) {
            throw NotFoundException(ErrorCode.CATEGORY_NOT_FOUND)
        }

        val addressId: Long = if (createCourseDto.address != null) {
            val address = this.addressService.save(createCourseDto.address)
            address.id
        } else {
            0
        }

        val course = Course.of(
            createCourseDto = createCourseDto,
            accountId = accountId,
            addressId = addressId
        )

        return this.courseRepository.save(course)
    }

    /**
     * 강의 정보를 수정한다.
     *
     * @param updateCourseRequest 수정 정보
     * @param accountId
     * */
    @Transactional
    fun update(updateCourseRequest: UpdateCourseDto, accountId: Long): Course {
        val course = this.courseRepository.findById(updateCourseRequest.id)

        if (course == null) {
            throw NotFoundException(ErrorCode.COURSE_NOT_FOUND)
        }

        val existsCategory = this.categoryService.existsById(updateCourseRequest.categoryId)
        if (!existsCategory) {
            throw NotFoundException(ErrorCode.CATEGORY_NOT_FOUND)
        }

        val addressId: Long = if (updateCourseRequest.address != null) {
            val address = this.addressService.save(updateCourseRequest.address)
            address.id
        } else {
            0
        }
        course.update(updateCourseRequest, accountId, addressId)

        return this.courseRepository.save(course)
    }

    /**
     * 강의를 오픈한다.
     * */
    @Transactional
    fun open(id: Long, accountId: Long) {
        val course = this.courseRepository.findById(id) ?: throw NotFoundException(ErrorCode.COURSE_NOT_FOUND)
        course.open(accountId)
        this.courseRepository.save(course)
    }

    /**
     * 강의를 삭제한다. 수강 중인 원생이 있을 경우 삭제할 수 없다.
     * */
    @Transactional
    fun delete(id: Long, accountId: Long) {
        val course = this.courseRepository.findById(id) ?: throw NotFoundException(ErrorCode.COURSE_NOT_FOUND)
        course.checkUpdatePermission(accountId)
        this.courseTicketReader.checkEnrolledStudents(id)
        this.courseRepository.deleteById(id)
    }

    /**
     * 수강 신청을 한다. 이미 수강 중인 강의는 신청할 수 없다.
     *
     * @param id 강의 아이디
     * @param studentId 수강할 학생 아이디
     * */
    @DistributedLock(key = "#id")
    fun enroll(id: Long, studentId: Long): CourseTicketSummary {
        val course = this.courseRepository.findById(id) ?: throw NotFoundException(ErrorCode.COURSE_NOT_FOUND)
        this.courseTicketReader.checkAlreadyEnrolled(id, studentId)
        val courseTicket = course.enroll(studentId)
        this.courseRepository.save(course)
        val savedCourseTicket = this.courseTicketWriter.save(courseTicket)
        return CourseTicketSummary(savedCourseTicket.id, DateUtil.toString(courseTicket.applicationDate))
    }

    @DistributedLock(key = "#id")
    fun decreaseEnrollmentStudent(id: Long, studentId: Long) {
        val course = this.courseRepository.findById(id) ?: throw NotFoundException(ErrorCode.COURSE_NOT_FOUND)
        course.decreaseNumberOfStudents()
        this.courseTicketReader.deleteByCourseIdAndStudentId(id, studentId)
    }

    fun findAll(paging: Paging): Page<CourseSummaryView> {
        return this.courseRepository.findSummaryView(CourseStatus.VISIBLE, paging)
    }

    fun findOne(id: Long): CourseDetailView {
        return this.courseRepository.findDetailView(id) ?: throw NotFoundException(ErrorCode.COURSE_NOT_FOUND)
    }

    fun existsById(courseId: Long): Boolean {
        return this.courseRepository.existsById(courseId)
    }

    fun findList(ids: List<Long>): List<CourseDetailView> {
        return this.courseRepository.findDetailViewList(ids)
    }

}