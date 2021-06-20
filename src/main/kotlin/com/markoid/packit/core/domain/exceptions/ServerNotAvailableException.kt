package com.markoid.packit.core.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ServerNotAvailableException(
    message: String = "Service is unavailable at the moment. Please retry later."
) : ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message)