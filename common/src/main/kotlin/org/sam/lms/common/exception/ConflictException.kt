package org.sam.lms.common.exception


class ConflictException(errorCode: ErrorCode) :
    HttpException(409, errorCode)