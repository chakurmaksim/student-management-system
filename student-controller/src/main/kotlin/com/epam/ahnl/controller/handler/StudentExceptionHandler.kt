package com.epam.ahnl.controller.handler

import com.epam.ahnl.service.exception.StudentNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class StudentExceptionHandler: ResponseEntityExceptionHandler() {

    companion object {
        private val currentLogger = LoggerFactory.getLogger(StudentExceptionHandler::class.java)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<String> {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception)
    }

    @ExceptionHandler(StudentNotFoundException::class)
    fun handleNotFoundException(exception: StudentNotFoundException): ResponseEntity<String> {
        return createResponseEntity(HttpStatus.NOT_FOUND, exception)
    }

    override fun handleHttpMessageNotReadable(exception: HttpMessageNotReadableException, headers: HttpHeaders,
                                              status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        currentLogger.error("Exception: ", exception)
        return ResponseEntity(exception.rootCause?.message, headers, status)
    }

    private fun createResponseEntity(status: HttpStatus, exception: Exception): ResponseEntity<String> {
        currentLogger.error("Exception: ", exception)
        return ResponseEntity.status(status).body(exception.message)
    }
}