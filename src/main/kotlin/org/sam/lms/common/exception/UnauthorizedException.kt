package org.sam.lms.common.exception

import org.springframework.http.HttpStatus


class UnauthorizedException(errorCode: ErrorCode) :
    HttpException(HttpStatus.UNAUTHORIZED, errorCode)