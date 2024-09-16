package org.sam.lms.course.ui

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.sam.lms.account.domain.Account
import org.sam.lms.common.Page
import org.sam.lms.common.Paging
import org.sam.lms.course.application.CourseService
import org.sam.lms.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.course.application.payload.`in`.UpdateCourseDto
import org.sam.lms.course.application.payload.out.CourseDetailView
import org.sam.lms.course.application.payload.out.CourseSummaryView
import org.sam.lms.course.application.payload.out.CourseTicketSummary
import org.sam.lms.course.ui.dto.PagingCourseSummaryResponse
import org.sam.lms.infra.QueryStringArgument
import org.sam.lms.infra.security.annotation.AuthUser
import org.sam.lms.infra.security.annotation.StudentOnly
import org.sam.lms.infra.security.annotation.TeacherOnly
import org.sam.lms.infra.swagger.annotation.SwaggerCreatedResponse
import org.sam.lms.infra.swagger.annotation.SwaggerOkResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Tag(name = "강의")
@RestController
@RequestMapping("/api/v1/courses")
class CourseController(private val courseService: CourseService) {

    @Operation(summary = "강의 생성")
    @SwaggerCreatedResponse(summary = "강의 생성 성공", content = [Content(schema = Schema(implementation = CourseSummaryView::class))])
    @TeacherOnly
    @PostMapping
    fun create(@Valid @RequestBody createCourseDto: CreateCourseDto, @AuthUser account: Account): ResponseEntity<CourseSummaryView> {
        val courseSummary = this.courseService.create(createCourseDto, account.id)
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .replacePath("/api/v1/courses/{id}")
            .buildAndExpand(courseSummary.id)
            .toUri()
        return ResponseEntity.created(location).build()
    }

    @Operation(summary = "강의 정보 수정")
    @SwaggerOkResponse(summary = "강의 수정 성공",  content = [Content(schema = Schema(implementation = CourseSummaryView::class))])
    @TeacherOnly
    @PutMapping("/{id}")
    fun update(@Valid @RequestBody updateCourseDto: UpdateCourseDto, @AuthUser account: Account): ResponseEntity<CourseSummaryView> {
        val courseSummary = this.courseService.update(updateCourseDto, account.id)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "수강 신청")
    @SwaggerOkResponse(summary = "수강 신청 성공",  content = [Content(schema = Schema(implementation = PagingCourseSummaryResponse::class))])
    @StudentOnly
    @PostMapping("/{id}/enroll")
    fun enroll(@PathVariable id: Long, @AuthUser account: Account): ResponseEntity<CourseTicketSummary> {
        val courseTicketSummary = this.courseService.enroll(id, account.id)
        return ResponseEntity.ok(courseTicketSummary)
    }

    @Operation(summary = "강의 목록 조회")
    @GetMapping
    fun findAll(@QueryStringArgument paging: Paging): ResponseEntity<Page<CourseSummaryView>> {
        val courseSummaryPage = this.courseService.findAll(paging)
        return ResponseEntity.ok(courseSummaryPage)
    }

    @Operation(summary = "강의 정보 조회")
    @SwaggerOkResponse(summary = "조회 성공", content = [Content(schema = Schema(implementation = CourseDetailView::class))])
    @GetMapping("/{id}")
    fun findAll(@PathVariable id: Long): ResponseEntity<CourseDetailView> {
        val courseDetail = this.courseService.findOne(id)
        return ResponseEntity.ok(courseDetail)
    }


}