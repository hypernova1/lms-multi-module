package org.sam.lms.course.ui

import org.sam.lms.account.domain.Account
import org.sam.lms.course.application.CourseService
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.infra.security.AuthUser
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/courses")
class CourseController(private val courseService: CourseService) {

    @Secured("ROLE_TEACHER")
    @PostMapping
    fun create(@RequestBody createCourseDto: CreateCourseDto, @AuthUser account: Account) {
        this.courseService.create(createCourseDto, account.id)
    }

}