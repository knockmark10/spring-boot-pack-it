package com.markoid.packit.authentication.domain.usecases

import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.authentication.domain.requests.SignInRequest
import com.markoid.packit.authentication.domain.requests.UserType
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.core.presentation.utils.ApiConstants
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.Key
import java.util.*

class SignInUseCase(
    private val authRepository: AuthRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : BaseUseCase<SignInResult, SignInRequest>() {

    override fun postValidatedExecution(request: SignInRequest): ResponseEntity<SignInResult> {
        // Search for user or driver. If it's not found, throw an error
        val user = this.authRepository.getUserByEmail(request?.email!!)
            ?: this.authRepository.getDriverByEmail(request.email!!)
            ?: throw raiseException(ExceptionDictionary.USER_NOT_FOUND)

        // Check if password provided matches with the one stored in the database
        if (passwordMatches(request.password!!, user.password).not())
            throw raiseException(ExceptionDictionary.INVALID_CREDENTIALS)

        // Create a result object
        val result = SignInResult(
            id = user.id.toHexString(),
            name = user.name,
            lastName = user.lastName,
            token = generateJsonWebToken(user.email),
            userType = if (user is UserEntity) UserType.User else UserType.Driver
        )

        return buildResultMessage(result)
    }

    override fun onValidateRequest(request: SignInRequest): ValidationStatus = when {
        request.email.isNullOrEmpty() || request.password.isNullOrEmpty() ->
            ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else -> ValidationStatus.Success
    }

    private fun passwordMatches(passwordProvided: String, storedPassword: String): Boolean =
        this.bCryptPasswordEncoder.matches(passwordProvided, storedPassword)

    private fun generateJsonWebToken(username: String): String {
        val expiration = Date(System.currentTimeMillis() + ApiConstants.EXPIRATION_TIME)
        val claims: Claims = Jwts.claims().setSubject(username)
        val key: Key = Keys.hmacShaKeyFor(ApiConstants.KEY.toByteArray())
        return Jwts.builder()
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(expiration)
            .compact()
    }

}