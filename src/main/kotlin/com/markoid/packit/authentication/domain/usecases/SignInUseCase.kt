package com.markoid.packit.authentication.domain.usecases

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.authentication.domain.exceptions.UserNotFoundException
import com.markoid.packit.authentication.domain.requests.SignInRequest
import com.markoid.packit.authentication.domain.requests.UserType
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.core.domain.exceptions.IncompleteRequestException
import com.markoid.packit.core.domain.usecases.BaseUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class SignInUseCase(
    private val authRepository: AuthRepository
) : BaseUseCase<SignInResult, SignInRequest>() {

    // FIXME: We must provided an ID
    override fun execute(request: SignInRequest): ResponseEntity<SignInResult> = validateRequest(request) {
        // Search for user. If it's null, search for driver. If it doesn't exist, there is no user in the database.
        val user = this.authRepository.getUserByEmail(it.email) ?: this.authRepository.getDriverByEmail(it.email)
        ?: throw UserNotFoundException()
        val result = when (user) {
            is UserEntity -> SignInResult("", user.name, user.lastName, user.firebaseToken, UserType.User)
            is DriverEntity -> SignInResult("", user.name, user.lastName, user.firebaseToken, UserType.Driver)
            else -> throw IllegalStateException("Unknown user found.")
        }
        ResponseEntity.status(HttpStatus.OK).body(result)
    }

    private fun validateRequest(request: SignInRequest, block: (SignInRequest) -> ResponseEntity<SignInResult>) = when {
        // Check incoming parameters. If they're empty, throw an exception. This will be handled in the controller
        request.email.isEmpty() || request.password.isEmpty() -> throw IncompleteRequestException()
        else -> block.invoke(request)
    }

}