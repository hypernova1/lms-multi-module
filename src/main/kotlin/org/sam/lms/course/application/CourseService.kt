package org.sam.lms.course.application

import jakarta.transaction.Transactional
import org.sam.lms.address.application.AddressService
import org.sam.lms.common.Page
import org.sam.lms.common.Paging
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.NotFoundException
import org.sam.lms.common.util.DateUtil
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.`in`.UpdateCourseDto
import org.sam.lms.course.application.payload.out.CourseDetailView
import org.sam.lms.course.application.payload.out.CourseSummaryView
import org.sam.lms.course.application.payload.out.CourseTicketSummary
import org.sam.lms.course.domain.*
import org.sam.lms.infra.lock.DistributedLock
import org.springframework.stereotype.Service

@Service
class CourseService(
    private val courseReader: CourseReader,
    private val courseWriter: CourseWriter,
    private val categoryReader: CategoryReader,
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
        val category = this.categoryReader.findOne(createCourseDto.categoryId)

        val addressId: Long = if (createCourseDto.address != null) {
            val address = this.addressService.save(createCourseDto.address)
            address.id
        } else {
            0
        }

        val course = Course.of(
            createCourseDto = createCourseDto,
            category = category,
            accountId = accountId,
            addressId = addressId
        );
        this.courseWriter.save(course)
        return course
    }

    /**
     * 강의 정보를 수정한다.
     *
     * @param updateCourseDto 수정 정보
     * @param accountId
     * */
    @Transactional
    fun update(updateCourseDto: UpdateCourseDto, accountId: Long): Course {
        val course = this.courseReader.findOne(updateCourseDto.id)
        val category = this.categoryReader.findOne(updateCourseDto.categoryId)
        val addressId: Long = if (updateCourseDto.address != null) {
            val address = this.addressService.save(updateCourseDto.address)
            address.id
        } else {
            0
        }
        course.update(updateCourseDto, category, accountId, addressId)
        this.courseWriter.save(course)
        return course
    }

    /**
     * 강의를 오픈한다.
     * */
    @Transactional
    fun open(id: Long, accountId: Long) {
        val course = this.courseReader.findOne(id)
        course.open(accountId)
        this.courseWriter.save(course)
    }

    /**
     * 강의를 삭제한다. 수강 중인 원생이 있을 경우 삭제할 수 없다.
     * */
    @Transactional
    fun delete(id: Long, accountId: Long) {
        val course = this.courseReader.findOne(id)
        course.checkUpdatePermission(accountId)
        this.courseTicketReader.checkEnrolledStudents(id)
        this.courseWriter.delete(id)
    }

    /**
     * 수강 신청을 한다. 이미 수강 중인 강의는 신청할 수 없다.
     *
     * @param id 강의 아이디
     * @param studentId 수강할 학생 아이디
     * */
    @DistributedLock(key = "#id")
    fun enroll(id: Long, studentId: Long): CourseTicketSummary {
        val course = this.courseReader.findOne(id)
        this.courseTicketReader.checkAlreadyEnrolled(id, studentId)
        val courseTicket = course.enroll(studentId)
        this.courseWriter.save(course)
        this.courseTicketWriter.save(courseTicket)
        return CourseTicketSummary(courseTicket.id, DateUtil.toString(courseTicket.applicationDate))
    }

    fun findAll(paging: Paging): Page<CourseSummaryView> {
        return this.courseReader.findAllPaging(paging)
    }

    fun findOne(id: Long): CourseDetailView {
        return this.courseReader.findDetail(id) ?: throw NotFoundException(ErrorCode.COURSE_NOT_FOUND)
    }

}