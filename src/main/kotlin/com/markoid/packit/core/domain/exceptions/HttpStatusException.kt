package com.markoid.packit.core.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class HttpStatusException(
    private val httpStatusCode: HttpStatus,
    private val exceptionMessage: String = "Internal server occurred",
    httpStatusException: Throwable? = null
) : ResponseStatusException(httpStatusCode, exceptionMessage, httpStatusException) {

    override val message: String
        get() = this.exceptionMessage

    override fun getStatus(): HttpStatus {
        return this.httpStatusCode
    }
}