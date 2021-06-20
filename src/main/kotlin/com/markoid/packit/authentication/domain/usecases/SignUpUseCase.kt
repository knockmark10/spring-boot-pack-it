package com.markoid.packit.authentication.domain.usecases

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.authentication.domain.requests.SignUpRequest
import com.markoid.packit.authentication.domain.requests.UserType
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : BaseUseCase<BaseResponse, SignUpRequest>() {

    override fun execute(request: SignUpRequest): ResponseEntity<BaseResponse> = when (request.userType) {
        UserType.User -> createUser(request)
        UserType.Driver -> createDriver(request)
    }

    private fun createUser(request: SignUpRequest): ResponseEntity<BaseResponse> {
        // Check if user is not taken
        val existingUser = this.authRepository.getUserByEmail(request.email)
        // If it's null it means it doesn't exist, so we can create one
        return if (existingUser == null) {
            val user = UserEntity(
                email = request.email,
                lastName = request.lastName,
                name = request.name,
                password = bCryptPasswordEncoder.encode(request.password)
            )
            this.authRepository.saveUser(user)
            buildOkMessage(ExceptionDictionary.USER_CREATED_SUCCESSFULLY)
        } else {
            // User is taken. Return an error
            throw raiseException(ExceptionDictionary.USER_ALREADY_EXISTS)
        }
    }

    private fun createDriver(request: SignUpRequest): ResponseEntity<BaseResponse> {
        // Check if user is not taken
        val existingDriver = this.authRepository.getUserByEmail(request.email)
        // If it's null it means it doesn't exist, so we can create one
        return if (existingDriver == null) {
            val driver = DriverEntity(
                email = request.email,
                lastName = request.lastName,
                name = request.name,
                password = bCryptPasswordEncoder.encode(request.password)
            )
            this.authRepository.saveDriver(driver)
            buildOkMessage(ExceptionDictionary.DRIVER_CREATED_SUCCESSFULLY)
        } else {
            // User is taken. Return an error
            throw raiseException(ExceptionDictionary.DRIVER_ALREADY_EXISTS)
        }
    }

}
