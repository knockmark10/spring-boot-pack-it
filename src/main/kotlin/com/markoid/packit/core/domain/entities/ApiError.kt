package com.markoid.packit.core.domain.entities

import org.springframework.http.HttpStatus

data class ApiError(
    val status: HttpStatus,
    val message: String,
    val error: List<String>
)