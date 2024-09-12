package org.sam.lms.common.exception

import org.springframework.http.HttpStatus

class BadRequestException(errorCode: ErrorCode) :
    HttpException(HttpStatus.BAD_REQUEST, errorCode)