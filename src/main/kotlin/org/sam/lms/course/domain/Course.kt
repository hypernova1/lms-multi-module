package org.sam.lms.course.domain

import org.sam.lms.common.exception.ConflictException
import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.ForbiddenException
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.`in`.UpdateCourseDto

class Course(
    val id: Long = 0L,
    var title: String,
    var description: String,
    var numberOfStudents: Int = 0,
    var category: Category,
    var price: Int = 0,
    var visible: Boolean = false,
    val teacherId: Long,
) {

    /**
     * 강의 정보를 수정한다.
     *
     * @param updateCourseDto 업데이트할 정보
     * @param category 카테고리
     * @param accountId 수정할 강사 아이디
     * */
    fun update(updateCourseDto: UpdateCourseDto, category: Category, accountId: Long) {
        this.checkUpdatePermission(accountId)

        this.title = updateCourseDto.title
        this.description = updateCourseDto.description
        this.price = updateCourseDto.price
        this.category = category
    }

    /**
     * 강의를 오픈한다.
     *
     * @param accountId 강사 아이디
     * */
    fun open(accountId: Long) {
        this.checkUpdatePermission(accountId)
        if (this.visible) {
            throw ConflictException(ErrorCode.ALREADY_VISIBLE)
        }
        this.visible = true
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

    companion object {
        fun of(createCourseDto: CreateCourseDto, category: Category, accountId: Long): Course {
            return Course(
                title = createCourseDto.title,
                description = createCourseDto.description,
                category = category,
                teacherId = accountId,
                price = createCourseDto.price,
            )
        }
    }

}