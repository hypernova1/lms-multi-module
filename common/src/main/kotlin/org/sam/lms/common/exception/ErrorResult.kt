package org.sam.lms.common.exception

class ErrorResult(var errorCode: String, var errorMessage: String) {
    var data: Any? = null

    constructor(errorCode: String, errorMessage: String, data: Any?) : this(errorCode, errorMessage) {
        this.data = data
    }
}