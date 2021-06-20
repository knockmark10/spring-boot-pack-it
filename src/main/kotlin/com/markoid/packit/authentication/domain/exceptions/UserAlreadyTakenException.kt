package com.markoid.packit.authentication.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UserAlreadyTakenException(
    message: String = "User account already exists. Please enter a different account."
) : ResponseStatusException(HttpStatus.BAD_REQUEST, message)