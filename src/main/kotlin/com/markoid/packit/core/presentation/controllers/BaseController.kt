package com.markoid.packit.core.presentation.controllers

import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.exceptions.IncompleteRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

abstract class BaseController {

    @ExceptionHandler(IncompleteRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIncompleteRequestException(exception: IncompleteRequestException): ResponseEntity<BaseResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse(ApiState.Error, exception.reason ?: exception.message))

}