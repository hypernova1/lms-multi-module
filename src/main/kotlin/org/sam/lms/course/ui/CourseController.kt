package org.sam.lms.course.ui

import org.sam.lms.account.domain.Account
import org.sam.lms.course.application.CourseService
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.`in`.UpdateCourseDto
import org.sam.lms.course.application.payload.out.CourseSummary
import org.sam.lms.course.application.payload.out.CourseTicketSummary
import org.sam.lms.infra.security.annotation.AuthUser
import org.sam.lms.infra.security.annotation.StudentOnly
import org.sam.lms.infra.security.annotation.TeacherOnly
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/v1/courses")
class CourseController(private val courseService: CourseService) {

    @TeacherOnly
    @PostMapping
    fun create(@RequestBody createCourseDto: CreateCourseDto, @AuthUser account: Account): ResponseEntity<CourseSummary> {
        val courseSummary = this.courseService.create(createCourseDto, account.id)
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .replacePath("/api/v1/courses/{id}")
            .buildAndExpand(courseSummary.id)
            .toUri()
        return ResponseEntity.created(location).body(courseSummary)
    }

    @TeacherOnly
    @PutMapping("/{id}")
    fun update(@RequestBody updateCourseDto: UpdateCourseDto, @AuthUser account: Account): ResponseEntity<CourseSummary> {
        val courseSummary = this.courseService.update(updateCourseDto, account.id)
        return ResponseEntity.ok(courseSummary)
    }

    @StudentOnly
    @PostMapping("/{id}/enroll")
    fun enroll(@PathVariable id: Long, @AuthUser account: Account): ResponseEntity<CourseTicketSummary> {
        val courseTicketSummary = this.courseService.enroll(id, account.id)
        return ResponseEntity.ok(courseTicketSummary)
    }


}