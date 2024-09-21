package org.sam.lms.client.course

import org.sam.lms.client.course.payload.CourseDetail
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "localhost:8081")
interface CourseClient {

    @GetMapping("/lms/api/v1/courses/{id}")
    fun getCourseDetail(@PathVariable id: Long): CourseDetail

}