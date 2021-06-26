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
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping(ApiConstants.AUTH_PATH)
@RestController
class AuthController(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    companion object {
        const val SIGN_IN_LOG = "A successful login has been performed for user {} {}"
        const val SIGN_UP_LOG = "New user has signed up into our system {}"
    }

    @ApiOperation("Performs authentication with user-password credentials.")
    @ApiResponse(code = 200, message = "User's information returned.")
    @PostMapping(ApiConstants.SIGN_IN_URL)
    fun signIn(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @Valid @RequestBody(required = false) body: SignInEntityDto?
    ): ResponseEntity<SignInResult> {
        val result = this.signInUseCase.setLanguage(language).startCommand(body)
        this.logger.info(SIGN_IN_LOG, result.name, result.lastName)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("Registers a new user or driver into the system.")
    @ApiResponse(code = 200, message = "A message indicating the signing up process.")
    @PostMapping(ApiConstants.SIGN_UP_URL)
    fun signUp(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @Valid @RequestBody(required = false) body: SignUpEntityDto?
    ): ResponseEntity<BaseResponse> {
        val result = this.signUpUseCase.setLanguage(language).startCommand(body)
        this.logger.info(SIGN_UP_LOG, body)
        return ResponseEntity.ok(result)
    }

}