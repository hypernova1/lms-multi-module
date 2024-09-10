package org.sam.lms.common.exception

import org.springframework.http.HttpStatus


class ConflictException(errorCode: ErrorCode) :
    HttpException(HttpStatus.CONFLICT, errorCode)