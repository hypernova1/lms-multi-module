package org.sam.lms.domain.course.domain

import org.sam.lms.common.exception.BadRequestException
import org.sam.lms.common.exception.ConflictException
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.ForbiddenException
import org.sam.lms.domain.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.domain.course.application.payload.`in`.UpdateCourseDto

data class Course(
    val id: Long = 0L,
    var title: String,
    var description: String,
    var numberOfStudents: Int = 0,
    var price: Int = 0,
    var type: CourseType,
    var status: CourseStatus = CourseStatus.HIDDEN,
    var offlineInfo: OfflineCourseInfo? = null,
    var categoryId: Long,
    val teacherId: Long
) {

    /**
     * 강의 정보를 수정한다.
     *
     * @param updateCourseRequest 업데이트할 정보
     * @param accountId 수정할 강사 아이디
     * */
    fun update(updateCourseRequest: UpdateCourseDto, accountId: Long, addressId: Long) {
        this.checkUpdatePermission(accountId)

        this.title = updateCourseRequest.title
        this.description = updateCourseRequest.description
        this.type = updateCourseRequest.type
        this.price = updateCourseRequest.price
        this.categoryId = updateCourseRequest.categoryId

        if (this.type == CourseType.OFFLINE) {
            this.offlineInfo = OfflineCourseInfo(maxEnrollment = updateCourseRequest.maxEnrollment, addressId = addressId)
        }
    }

    /**
     * 강의를 오픈한다.
     *
     * @param accountId 강사 아이디
     * */
    fun open(accountId: Long) {
        this.checkUpdatePermission(accountId)
        if (this.status !== CourseStatus.HIDDEN) {
            throw ConflictException(ErrorCode.ALREADY_VISIBLE)
        }
        this.status = CourseStatus.VISIBLE
    }

    /**
     * 수정 권한이 있는지 체크한다
     *
     * @param accountId 수정할 강사 아이디
     * */
    fun checkUpdatePermission(accountId: Long) {
        if (this.teacherId != accountId) {
            throw ForbiddenException()
        }
    }

    /**
     * 강의 수강 신청한다.
     *
     * @param studentId 수강할 학생 아이디
     * @return 수강권
     * */
    fun enroll(studentId: Long): CourseTicket {
        checkMaxEnrollment()
        increaseNumberOfStudents()
        return CourseTicket(courseId = this.id, studentId = studentId)
    }

    fun increaseNumberOfStudents() {
        this.numberOfStudents++
    }

    fun decreaseNumberOfStudents() {
        this.numberOfStudents--
    }

    private fun checkMaxEnrollment() {
        if (this.type == CourseType.ONLINE) {
            return
        }

        if (this.offlineInfo?.maxEnrollment != null && this.offlineInfo?.maxEnrollment!! == this.numberOfStudents) {
            throw BadRequestException(ErrorCode.ENROLLMENT_FULL)
        }
    }

    companion object {
        fun of(createCourseDto: CreateCourseDto, accountId: Long, addressId: Long): Course {
            val course = Course(
                title = createCourseDto.title,
                description = createCourseDto.description,
                categoryId = createCourseDto.categoryId,
                teacherId = accountId,
                price = createCourseDto.price,
                type = createCourseDto.type,
            )

            if (createCourseDto.type == CourseType.OFFLINE) {
                course.offlineInfo = OfflineCourseInfo(maxEnrollment = createCourseDto.maxEnrollment, addressId = addressId)
            }

            return course
        }
    }

    override fun toString(): String {
        return "id=${this.id} title=${this.title}"
    }
}