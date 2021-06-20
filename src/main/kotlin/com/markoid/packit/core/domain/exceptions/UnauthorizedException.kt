package com.markoid.packit.core.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

/**
 * HTTP Status Code 401 - Bad Request
 */
class UnauthorizedException(
    private val exceptionMessage: String = "Unauthorized"
) : ResponseStatusException(HttpStatus.UNAUTHORIZED, exceptionMessage) {

    override val message: String
        get() = exceptionMessage

}