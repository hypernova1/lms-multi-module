package org.sam.lms.common.exception


class NotFoundException(errorCode: ErrorCode) :
    HttpException(404, errorCode)