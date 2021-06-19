package com.markoid.packit.core.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class IncompleteRequestException(
    message: String = "This request could no tbe processed with the information provided."
) : ResponseStatusException(HttpStatus.BAD_REQUEST, message)