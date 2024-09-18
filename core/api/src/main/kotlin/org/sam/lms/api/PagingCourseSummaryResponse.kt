package org.sam.lms.api

import org.sam.lms.domain.common.Page
import org.sam.lms.domain.course.application.payload.out.CourseSummaryView

class PagingCourseSummaryResponse : Page<CourseSummaryView>(1, 10, 1, listOf())