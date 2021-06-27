package com.markoid.packit.authentication.domain.usecases

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.authentication.domain.requests.SignUpEntityDto
import com.markoid.packit.authentication.domain.requests.UserType
import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : AbstractUseCase<ApiResult, SignUpEntityDto>() {

    override fun onExecuteTask(params: SignUpEntityDto): ApiResult {
        // Look for an user or a driver.
        val existingUser =
            this.authRepository.getUserByEmail(params.email) ?: this.authRepository.getDriverByEmail(params.email)

        // If it's not null it means the desired user is taken. Throw an error based on the user type found.
        when {
            existingUser != null && existingUser is UserEntity -> throw raiseException(ExceptionDictionary.USER_ALREADY_EXISTS)
            existingUser != null && existingUser is DriverEntity -> throw raiseException(ExceptionDictionary.DRIVER_ALREADY_EXISTS)
        }

        // Create desired user based on the user type provided.
        return if (params.userType == UserType.User) {
            val user = UserEntity(
                email = params.email,
                lastName = params.lastName,
                name = params.name,
                password = bCryptPasswordEncoder.encode(params.password)
            )
            // Save user on the system
            this.authRepository.saveUser(user)
            // Return ok message
            buildSuccessfulMessage(ExceptionDictionary.USER_CREATED_SUCCESSFULLY)
        } else {
            val driver = DriverEntity(
                email = params.email,
                lastName = params.lastName,
                name = params.name,
                password = bCryptPasswordEncoder.encode(params.password)
            )
            // Save user on the system
            this.authRepository.saveDriver(driver)
            // Return ok message
            buildSuccessfulMessage(ExceptionDictionary.DRIVER_CREATED_SUCCESSFULLY)
        }
    }

}
