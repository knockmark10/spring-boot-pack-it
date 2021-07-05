package com.markoid.packit.authentication.presentation.controllers

import com.markoid.packit.authentication.data.service.implementation.AuthServiceImpl
import com.markoid.packit.authentication.domain.requests.SignInEntityDto
import com.markoid.packit.authentication.domain.requests.SignUpEntityDto
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.presentation.utils.ApiConstants
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RequestMapping(ApiConstants.AUTH_PATH)
@RestController
class AuthController(private val authService: AuthServiceImpl) {

    @ApiOperation("Performs authentication with user-password credentials.")
    @ApiResponse(code = 200, message = "User's information returned.")
    @PostMapping(ApiConstants.SIGN_IN_URL)
    fun signIn(
        @RequestBody(required = false) @Valid body: SignInEntityDto?
    ): ResponseEntity<SignInResult> {
        val result = this.authService.signIn(body)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("Registers a new user or driver into the system.")
    @ApiResponse(code = 200, message = "A message indicating the signing up process.")
    @PostMapping(ApiConstants.SIGN_UP_URL)
    fun signUp(
        @RequestBody @Valid body: SignUpEntityDto?
    ): ResponseEntity<ApiResult> {
        val result = this.authService.signUp(body)
        return ResponseEntity.ok(result)
    }

}