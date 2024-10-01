package org.sam.lms.client.course

import org.sam.lms.client.course.payload.CourseDetailResponse
import org.sam.lms.client.course.payload.CourseEnrollResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "localhost:8081")
interface CourseClient {

    @GetMapping("/lms/api/v1/courses/{id}")
    fun getCourseDetail(@PathVariable id: Long): CourseDetailResponse

    @GetMapping("/lms/api/v1/courses")
    fun getCourseList(courseIds: List<Long>): List<CourseDetailResponse>

    @PostMapping("/lms/api/v1/courses/{id}/enroll")
    fun enrollCourse(@PathVariable id: Long, @RequestBody studentId: Long): CourseEnrollResponse

    @PatchMapping("/lms/api/v1/courses/{id}/decrease-enrollments")
    fun decreaseEnrollment(@PathVariable id: Long, @RequestBody studentId: Long): CourseEnrollResponse

}