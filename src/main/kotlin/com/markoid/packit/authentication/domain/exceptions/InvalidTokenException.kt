package com.markoid.packit.authentication.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidTokenException(
    message: String = "Failed to authenticate token."
) : ResponseStatusException(HttpStatus.UNAUTHORIZED, message)