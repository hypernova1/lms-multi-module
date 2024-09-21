package org.sam.lms.api.common

import io.swagger.v3.oas.annotations.media.Schema

class PageRequest(
    @field:Schema(description = "사이즈", example = "10", required = false)
    var size: Int = 10,

    @field:Schema(description = "페이지", example = "1", required = false)
    var page: Int = 1
)