package com.kvsinyuk.core.adapter.`in`.http

import com.kvsinyuk.core.application.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ControllerAdvice: ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(): ResponseEntity<Any> {
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
