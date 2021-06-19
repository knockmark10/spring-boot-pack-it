package com.markoid.packit.authentication.domain.usecases

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.authentication.domain.requests.SignUpRequest
import com.markoid.packit.authentication.domain.requests.UserType
import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import org.springframework.http.HttpStatus
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
            ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse(ApiState.Success, "User account has been created successfully."))
        } else {
            // User is taken. Return an error
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse(ApiState.Error, "User account already exists. Please enter a different account."))
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
            ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse(ApiState.Success, "Driver account has been created successfully."))
        } else {
            // User is taken. Return an error
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse(ApiState.Error, "Driver account already exists. Please enter a different account."))
        }
    }

}
