package com.markoid.packit.authentication.presentation.controllers

import com.markoid.packit.authentication.domain.exceptions.UserNotFoundException
import com.markoid.packit.authentication.domain.requests.SignInRequest
import com.markoid.packit.authentication.domain.requests.SignUpRequest
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.authentication.domain.usecases.SignInUseCase
import com.markoid.packit.authentication.domain.usecases.SignUpUseCase
import com.markoid.packit.authentication.presentation.utils.AuthConstants
import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.presentation.controllers.BaseAuthController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(AuthConstants.AUTH_PATH)
@RestController
class AuthController(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : BaseAuthController() {

    @PostMapping(AuthConstants.SIGN_IN_URL)
    fun signIn(@RequestBody body: SignInRequest): ResponseEntity<SignInResult> {
        return this.signInUseCase.execute(body)
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<BaseResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse(ApiState.Error, exception.reason ?: exception.message))

    @PostMapping(AuthConstants.SIGN_UP_URL)
    fun signUp(@RequestBody body: SignUpRequest): ResponseEntity<BaseResponse> {
        return this.signUpUseCase.execute(body)
    }

}