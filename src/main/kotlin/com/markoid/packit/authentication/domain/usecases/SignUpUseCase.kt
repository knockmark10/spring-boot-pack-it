package com.markoid.packit.authentication.domain.usecases

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.authentication.domain.requests.SignUpEntityDto
import com.markoid.packit.authentication.domain.requests.UserType
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : BaseUseCase<BaseResponse, SignUpEntityDto>() {

    override fun postValidatedExecution(request: SignUpEntityDto): BaseResponse {
        // Look for an user or a driver.
        val existingUser =
            this.authRepository.getUserByEmail(request.email!!) ?: this.authRepository.getDriverByEmail(request.email)

        // If it's not null it means the desired user is taken. Throw an error based on the user type found.
        when {
            existingUser != null && existingUser is UserEntity -> throw raiseException(ExceptionDictionary.USER_ALREADY_EXISTS)
            existingUser != null && existingUser is DriverEntity -> throw raiseException(ExceptionDictionary.DRIVER_ALREADY_EXISTS)
        }

        // Create desired user based on the user type provided.
        return if (request.userType == UserType.User) {
            val user = UserEntity(
                email = request.email,
                lastName = request.lastName!!,
                name = request.name!!,
                password = bCryptPasswordEncoder.encode(request.password)
            )
            // Save user on the system
            this.authRepository.saveUser(user)
            // Return ok message
            buildOkMessage(ExceptionDictionary.USER_CREATED_SUCCESSFULLY)
        } else {
            val driver = DriverEntity(
                email = request.email,
                lastName = request.lastName!!,
                name = request.name!!,
                password = bCryptPasswordEncoder.encode(request.password)
            )
            // Save user on the system
            this.authRepository.saveDriver(driver)
            // Return ok message
            buildOkMessage(ExceptionDictionary.DRIVER_CREATED_SUCCESSFULLY)
        }
    }

    override fun onValidateRequest(request: SignUpEntityDto): ValidationStatus = when {
        request.name.isNullOrEmpty() ||
                request.lastName.isNullOrEmpty() ||
                request.email.isNullOrEmpty() ||
                request.password.isNullOrEmpty() ||
                request.userType == null -> ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else -> ValidationStatus.Success
    }

}
