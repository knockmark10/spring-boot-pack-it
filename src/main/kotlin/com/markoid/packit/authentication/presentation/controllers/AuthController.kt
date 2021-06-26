package com.markoid.packit.authentication.presentation.controllers

import com.markoid.packit.authentication.domain.requests.SignInEntityDto
import com.markoid.packit.authentication.domain.requests.SignUpEntityDto
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.authentication.domain.usecases.SignInUseCase
import com.markoid.packit.authentication.domain.usecases.SignUpUseCase
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.presentation.utils.ApiConstants
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping(ApiConstants.AUTH_PATH)
@RestController
class AuthController(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) {

    @ApiOperation("Performs authentication with user-password credentials.")
    @ApiResponse(code = 200, message = "User's information returned.")
    @PostMapping(ApiConstants.SIGN_IN_URL)
    fun signIn(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @Valid @RequestBody body: SignInEntityDto?
    ): ResponseEntity<SignInResult> = this.signInUseCase.setLanguage(language).startCommand(body)

    @ApiOperation("Registers a new uer or driver into the system.")
    @ApiResponse(code = 200, message = "A message indicating the signing up process.")
    @PostMapping(ApiConstants.SIGN_UP_URL)
    fun signUp(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @Valid @RequestBody body: SignUpEntityDto?
    ): ResponseEntity<BaseResponse> = this.signUpUseCase.setLanguage(language).startCommand(body)

}