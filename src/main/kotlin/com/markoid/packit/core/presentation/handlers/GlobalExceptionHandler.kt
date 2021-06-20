package com.markoid.packit.core.presentation.handlers

import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.exceptions.HttpStatusException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpStatusException::class)
    fun handleExceptions(exception: Throwable): ResponseEntity<BaseResponse> = when (exception) {
        is HttpStatusException -> ResponseEntity.status(exception.status)
            .body(BaseResponse(ApiState.Error, exception.reason ?: exception.message))
        else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(BaseResponse(ApiState.Error, "Server is not available at the moment."))
    }

}