package com.markoid.packit.authentication.presentation.controllers

import com.markoid.packit.authentication.domain.requests.SignInRequest
import com.markoid.packit.authentication.domain.requests.SignUpRequest
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.authentication.domain.usecases.SignInUseCase
import com.markoid.packit.authentication.domain.usecases.SignUpUseCase
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.presentation.utils.ApiConstants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(ApiConstants.AUTH_PATH)
@RestController
class AuthController(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) {

    @PostMapping(ApiConstants.SIGN_IN_URL)
    fun signIn(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody body: SignInRequest
    ): ResponseEntity<SignInResult> = this.signInUseCase.setLanguage(language).execute(body)

    @PostMapping(ApiConstants.SIGN_UP_URL)
    fun signUp(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody body: SignUpRequest
    ): ResponseEntity<BaseResponse> = this.signUpUseCase.setLanguage(language).execute(body)

}