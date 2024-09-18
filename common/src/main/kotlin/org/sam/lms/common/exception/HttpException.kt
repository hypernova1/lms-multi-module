package org.sam.lms.common.exception

open class HttpException(val status: Int, val errorCode: ErrorCode?, val data: Any?) : RuntimeException() {

    constructor(status: Int) : this(status, null, null)

    constructor(status: Int, errorCode: ErrorCode?) : this(status, errorCode, null)

}