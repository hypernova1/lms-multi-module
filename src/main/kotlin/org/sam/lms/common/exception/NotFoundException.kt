package org.sam.lms.common.exception

import org.springframework.http.HttpStatus


class NotFoundException(errorCode: ErrorCode) :
    HttpException(HttpStatus.NOT_FOUND, errorCode)