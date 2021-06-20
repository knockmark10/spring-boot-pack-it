package com.markoid.packit.core.presentation.handlers

import com.markoid.packit.authentication.domain.exceptions.UserAlreadyTakenException
import com.markoid.packit.authentication.domain.exceptions.UserNotFoundException
import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.exceptions.IncompleteRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class, IncompleteRequestException::class, UserAlreadyTakenException::class)
    fun handleExceptions(exception: Exception): ResponseEntity<BaseResponse> = when (exception) {
        is UserNotFoundException -> ResponseEntity.status(exception.status)
            .body(BaseResponse(ApiState.Error, exception.reason ?: exception.message))
        is IncompleteRequestException -> ResponseEntity.status(exception.status)
            .body(BaseResponse(ApiState.Error, exception.reason ?: exception.message))
        is UserAlreadyTakenException -> ResponseEntity.status(exception.status)
            .body(BaseResponse(ApiState.Error, exception.reason ?: exception.message))
        else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(BaseResponse(ApiState.Error, "Server is not available at the moment."))
    }

}