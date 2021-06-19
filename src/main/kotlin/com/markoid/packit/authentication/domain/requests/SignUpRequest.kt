package com.markoid.packit.authentication.domain.requests

data class SignUpRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val userType: UserType
)