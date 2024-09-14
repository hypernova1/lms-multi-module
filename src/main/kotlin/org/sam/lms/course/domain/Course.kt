package org.sam.lms.course.domain

import org.sam.lms.common.exception.ConflictException
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.ForbiddenException
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.`in`.UpdateCourseDto

class Course(
    var id: Long = 0L,
    var title: String,
    var description: String,
    var numberOfStudents: Int = 0,
    var category: Category,
    var price: Int = 0,
    var status: CourseStatus = CourseStatus.HIDDEN,
    var type: CourseType,
    val teacherId: Long,
    var offlineInfo: OfflineCourseInfo? = null
) {

    /**
     * 강의 정보를 수정한다.
     *
     * @param updateCourseDto 업데이트할 정보
     * @param category 카테고리
     * @param accountId 수정할 강사 아이디
     * */
    fun update(updateCourseDto: UpdateCourseDto, category: Category, accountId: Long, addressId: Long) {
        this.checkUpdatePermission(accountId)

        this.title = updateCourseDto.title
        this.description = updateCourseDto.description
        this.type = updateCourseDto.type
        this.price = updateCourseDto.price
        this.category = category

        if (this.type == CourseType.OFFLINE) {
            this.offlineInfo = OfflineCourseInfo(maxEnrollment = updateCourseDto.maxEnrollment, addressId = addressId)
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
        this.numberOfStudents++
        return CourseTicket(courseId = this.id, studentId = studentId)
    }

    companion object {
        fun of(createCourseDto: CreateCourseDto, category: Category, accountId: Long, addressId: Long): Course {
            val course = Course(
                title = createCourseDto.title,
                description = createCourseDto.description,
                category = category,
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