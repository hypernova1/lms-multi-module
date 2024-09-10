package org.sam.lms.common.exception

import org.springframework.http.HttpStatus

open class HttpException(val status: HttpStatus, val errorCode: ErrorCode?, val data: Any?) : RuntimeException() {

    constructor(status: HttpStatus) : this(status, null, null)

    constructor(status: HttpStatus, errorCode: ErrorCode) : this(status, errorCode, null)

}