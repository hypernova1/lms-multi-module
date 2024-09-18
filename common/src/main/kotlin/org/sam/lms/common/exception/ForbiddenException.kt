package org.sam.lms.common.exception

class ForbiddenException(errorCode: ErrorCode?) :
    HttpException(403, errorCode) {
        constructor(): this(null)
    }