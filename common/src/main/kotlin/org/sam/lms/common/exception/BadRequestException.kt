package org.sam.lms.common.exception

class BadRequestException(errorCode: ErrorCode) :
    HttpException(400, errorCode)