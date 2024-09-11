package org.sam.lms.common.exception

import org.springframework.http.HttpStatus

class ForbiddenException(errorCode: ErrorCode?) :
    HttpException(HttpStatus.FORBIDDEN, errorCode) {
        constructor(): this(null)
    }