package com.markoid.packit.authentication.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class AccessNotGrantedException(
    message: String = "Access not granted for this resource."
) : ResponseStatusException(HttpStatus.FORBIDDEN, message)