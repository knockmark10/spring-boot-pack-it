package com.markoid.packit.core.presentation.controllers

import com.markoid.packit.authentication.domain.exceptions.AccessNotGrantedException
import com.markoid.packit.authentication.domain.exceptions.InvalidTokenException
import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

abstract class BaseAuthController: BaseController() {

    @ExceptionHandler(AccessNotGrantedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessNotGrantedException(exception: AccessNotGrantedException): ResponseEntity<BaseResponse> =
        ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(BaseResponse(ApiState.Error, exception.reason ?: exception.message))

    @ExceptionHandler(InvalidTokenException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInvalidTokenException(exception: InvalidTokenException): ResponseEntity<BaseResponse> =
        ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(BaseResponse(ApiState.Error, exception.reason ?: exception.message))

}