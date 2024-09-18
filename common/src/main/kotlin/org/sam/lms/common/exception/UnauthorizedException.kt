package org.sam.lms.common.exception


class UnauthorizedException(errorCode: ErrorCode) :
    HttpException(401, errorCode)