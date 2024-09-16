package org.sam.lms.course.ui.dto

import org.sam.lms.common.Page
import org.sam.lms.course.application.payload.out.CourseSummaryView

class PagingCourseSummaryResponse : Page<CourseSummaryView>(1, 10, 1, listOf())