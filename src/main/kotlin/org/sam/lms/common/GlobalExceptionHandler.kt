package org.sam.lms.common

import org.sam.lms.common.exception.ErrorCode
import org.sam.lms.common.exception.ErrorResult
import org.sam.lms.common.exception.HttpException
import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException


@RestControllerAdvice
class GlobalExceptionHandler(
    private val log: Logger = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
) {

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

    @ExceptionHandler(Exception::class)
    protected fun exception(e: Exception): ResponseEntity<*> {
        log.error(e.message)
        return ResponseEntity.internalServerError().body(ErrorResult("9999", "서버에 오류가 발생했습니다."))
    }

}