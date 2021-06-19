package com.markoid.packit.authentication.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UserNotFoundException(
    message: String = "The requested user does not exist."
) : ResponseStatusException(HttpStatus.FORBIDDEN, message)