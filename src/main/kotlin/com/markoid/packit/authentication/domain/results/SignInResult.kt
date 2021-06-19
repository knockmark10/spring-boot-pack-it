package com.markoid.packit.authentication.domain.results

import com.markoid.packit.authentication.domain.requests.UserType

data class SignInResult(
    val id: String,
    val name: String,
    val lastName: String,
    val token: String,
    val userType: UserType
)