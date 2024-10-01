package org.sam.lms.api.course

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.sam.lms.api.course.response.PagingCourseSummaryResponse
import org.sam.lms.api.common.ui.QueryStringArgument
import org.sam.lms.api.config.AdminUser
import org.sam.lms.api.config.StudentUser
import org.sam.lms.api.config.TeacherUser
import org.sam.lms.api.course.request.*
import org.sam.lms.api.swagger.annotation.SwaggerCreatedResponse
import org.sam.lms.api.swagger.annotation.SwaggerOkResponse
import org.sam.lms.domain.account.domain.Provider
import org.sam.lms.domain.address.application.payload.`in`.CreateAddressDto
import org.sam.lms.domain.common.Page
import org.sam.lms.domain.common.Paging
import org.sam.lms.domain.course.application.CategoryService
import org.sam.lms.domain.course.application.CourseService
import org.sam.lms.domain.course.application.payload.CreateCategoryDto
import org.sam.lms.domain.course.application.payload.`in`.CreateCourseDto
import org.sam.lms.domain.course.application.payload.`in`.UpdateCourseDto
import org.sam.lms.domain.course.application.payload.out.CategorySummary
import org.sam.lms.domain.course.application.payload.out.CourseDetailView
import org.sam.lms.domain.course.application.payload.out.CourseSummaryView
import org.sam.lms.domain.course.application.payload.out.CourseTicketSummary
import org.sam.lms.domain.review.application.`in`.CreateReviewDto
import org.sam.lms.domain.review.application.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Tag(name = "강의")
@RestController
@RequestMapping("/lms/api/v1/courses")
class CourseController(
    private val courseService: CourseService,
    private val reviewService: ReviewService,
    private val categoryService: CategoryService
) {

    @Operation(summary = "강의 생성")
    @SwaggerCreatedResponse(
        summary = "강의 생성 성공",
        content = [Content(schema = Schema(implementation = CourseSummaryView::class))]
    )
    @PostMapping
    fun create(
        @Valid @RequestBody createCourseRequest: CreateCourseRequest,
        @TeacherUser provider: Provider
    ): ResponseEntity<CourseSummaryView> {
        val courseSummary = this.courseService.create(
            CreateCourseDto(
                title = createCourseRequest.title,
                description = createCourseRequest.description,
                price = createCourseRequest.price,
                categoryId = createCourseRequest.categoryId,
                type = createCourseRequest.type,
                maxEnrollments = createCourseRequest.maxEnrollments,
                address = if (createCourseRequest.address != null) {
                    CreateAddressDto(
                        si = createCourseRequest.address.si,
                        gugun = createCourseRequest.address.gugun,
                        doro = createCourseRequest.address.doro,
                        detail = createCourseRequest.address.detail,
                        zipcode = createCourseRequest.address.zipcode,
                    )
                } else {
                    null
                }
            ),
            accountId = provider.id
        )
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .replacePath("/api/v1/courses/{id}")
            .buildAndExpand(courseSummary.id)
            .toUri()
        return ResponseEntity.created(location).build()
    }

    @Operation(summary = "강의 정보 수정")
    @SwaggerOkResponse(
        summary = "강의 수정 성공",
        content = [Content(schema = Schema(implementation = CourseSummaryView::class))]
    )
    @PutMapping("/{id}")
    fun update(
        @Valid @RequestBody updateCourseRequest: UpdateCourseRequest,
        @TeacherUser provider: Provider
    ): ResponseEntity<CourseSummaryView> {
        this.courseService.update(
            UpdateCourseDto(
                title = updateCourseRequest.title,
                description = updateCourseRequest.description,
                price = updateCourseRequest.price,
                categoryId = updateCourseRequest.categoryId,
                type = updateCourseRequest.type,
                maxEnrollments = updateCourseRequest.maxEnrollments,
                address = if (updateCourseRequest.address != null) {
                    CreateAddressDto(
                        si = updateCourseRequest.address.si,
                        gugun = updateCourseRequest.address.gugun,
                        doro = updateCourseRequest.address.doro,
                        detail = updateCourseRequest.address.detail,
                        zipcode = updateCourseRequest.address.zipcode,
                    )
                } else {
                    null
                }
            ), provider.id
        )
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "수강 신청")
    @SwaggerOkResponse(
        summary = "수강 신청 성공",
        content = [Content(schema = Schema(implementation = CourseTicketSummary::class))]
    )
    @PostMapping("/{id}/enroll")
    fun enroll(@PathVariable id: Long, @StudentUser provider: Provider): ResponseEntity<CourseTicketSummary> {
        val courseTicketSummary = this.courseService.enroll(id, provider.id)
        return ResponseEntity.ok(courseTicketSummary)
    }

    @PostMapping("/list")
    fun getListByIds(@RequestBody courseListSearchRequest: CourseListSearchRequest): ResponseEntity<List<CourseDetailView>> {
        val courseDetailList = this.courseService.findList(courseListSearchRequest.courseIds)
        return ResponseEntity.ok(courseDetailList)
    }

    @Operation(summary = "수강 인원 감소")
    @SwaggerOkResponse(
        summary = "요청 성공"
    )
    @PostMapping("/{id}/decrease-enrollments")
    fun decreaseEnrollment(@PathVariable id: Long, @StudentUser provider: Provider): ResponseEntity<Void> {
        this.courseService.decreaseEnrollmentStudent(id, provider.id)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "강의 목록 조회")
    @SwaggerOkResponse(
        summary = "강의 목록 조회 성공",
        content = [Content(schema = Schema(implementation = PagingCourseSummaryResponse::class))]
    )
    @GetMapping
    fun findAll(@QueryStringArgument paging: Paging): ResponseEntity<Page<CourseSummaryView>> {
        val courseSummaryPage = this.courseService.findAll(paging)
        return ResponseEntity.ok(courseSummaryPage)
    }

    @Operation(summary = "강의 정보 조회")
    @SwaggerOkResponse(
        summary = "조회 성공",
        content = [Content(schema = Schema(implementation = CourseDetailView::class))]
    )
    @GetMapping("/{id}")
    fun findAll(@PathVariable id: Long): ResponseEntity<CourseDetailView> {
        val courseDetail = this.courseService.findOne(id)
        return ResponseEntity.ok(courseDetail)
    }

    @Operation(summary = "카테고리 목록 조회")
    @SwaggerOkResponse(
        summary = "카테고리 조회 성공",
        content = [Content(array = ArraySchema(schema = Schema(implementation = CategorySummary::class)))]
    )
    @GetMapping("/categories")
    fun findCategories(): ResponseEntity<List<CategorySummary>> {
        val categories = this.categoryService.findAll()
        return ResponseEntity.ok(categories.map { CategorySummary(it.id, it.name) })
    }

    @Operation(summary = "카테고리 등록")
    @SwaggerOkResponse(summary = "카테고리 등록 성공")
    @PostMapping("/categories")
    fun createCategory(@Valid @RequestBody createCategoryRequest: CreateCategoryRequest, @AdminUser provider: Provider): ResponseEntity<Void> {
        this.categoryService.create(CreateCategoryDto(createCategoryRequest.name))
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{id}/reviews")
    fun createReview(
        @PathVariable id: Long,
        provider: Provider,
        @RequestBody reviewRequest: ReviewRequest
    ): ResponseEntity<Any> {
        this.reviewService.registerReview(
            id,
            provider,
            CreateReviewDto(content = reviewRequest.content, score = reviewRequest.score)
        )
        return ResponseEntity.ok().build()
    }

}