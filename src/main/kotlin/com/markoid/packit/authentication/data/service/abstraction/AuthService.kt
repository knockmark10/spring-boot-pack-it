package com.markoid.packit.authentication.data.service.abstraction

import com.markoid.packit.authentication.domain.requests.SignInEntityDto
import com.markoid.packit.authentication.domain.requests.SignUpEntityDto
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.core.data.ApiResult

interface AuthService {
    fun signIn(request: SignInEntityDto?): SignInResult
    fun signUp(request: SignUpEntityDto?): ApiResult
}