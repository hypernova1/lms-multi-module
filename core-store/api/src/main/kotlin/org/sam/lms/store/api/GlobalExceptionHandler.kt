package org.sam.lms.store.api

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.ErrorResult
import org.sam.lms.common.exception.HttpException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpException::class)
    protected fun httpException(ex: HttpException): ResponseEntity<*> {
        return ResponseEntity.status(ex.status).body(ErrorResult(ex.errorCode!!.code, ex.errorCode!!.message, ex.data))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<*> {
        val allErrors = ex.allErrors

        val errorMessages: MutableList<String?> = ArrayList()
        for (error in allErrors) {
            errorMessages.add(error.defaultMessage)
        }

        return ResponseEntity.unprocessableEntity().body<ErrorResult>(
            ErrorResult(
                ErrorCode.MISMATCH_PARAMETER.code,
                ErrorCode.MISMATCH_PARAMETER.message,
                errorMessages
            )
        )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    protected fun noResourceFoundException(e: NoResourceFoundException?): ResponseEntity<*> {
        return ResponseEntity.notFound().build<Any>()
    }

}