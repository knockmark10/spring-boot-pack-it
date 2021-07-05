package com.markoid.packit.authentication.data.service.implementation

import com.markoid.packit.authentication.data.service.abstraction.AuthService
import com.markoid.packit.authentication.domain.requests.SignInEntityDto
import com.markoid.packit.authentication.domain.requests.SignUpEntityDto
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.authentication.domain.usecases.SignInUseCase
import com.markoid.packit.authentication.domain.usecases.SignUpUseCase
import com.markoid.packit.authentication.presentation.controllers.AuthController
import com.markoid.packit.core.data.ApiResult
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : AuthService {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    companion object {
        const val SIGN_IN_LOG = "A successful login has been performed for user {} {}"
        const val SIGN_UP_LOG = "New user has signed up into our system {}"
    }

    override fun signIn(request: SignInEntityDto?): SignInResult {
        val result = this.signInUseCase.startCommand(request)
        this.logger.info(SIGN_IN_LOG, result.name, result.lastName)
        return result
    }

    override fun signUp(request: SignUpEntityDto?): ApiResult {
        val result = this.signUpUseCase.startCommand(request)
        this.logger.info(SIGN_UP_LOG, request)
        return result
    }

}