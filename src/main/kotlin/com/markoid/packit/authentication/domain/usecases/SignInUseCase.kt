package com.markoid.packit.authentication.domain.usecases

import com.markoid.packit.authentication.data.entities.DriverEntity
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

    override fun execute(request: SignInRequest): ResponseEntity<SignInResult> = validateRequest(request) {
        // Search for user. If it's null, search for driver. If it doesn't exist, there is no user in the database.
        val user = this.authRepository.getUserByEmail(it.email) ?: this.authRepository.getDriverByEmail(it.email)
        val result = when (user) {
            is UserEntity -> SignInResult(
                user.id.toHexString(),
                user.name,
                user.lastName,
                generateJsonWebToken(user.email),
                UserType.User
            )
            is DriverEntity -> SignInResult(
                user.id.toHexString(),
                user.name,
                user.lastName,
                generateJsonWebToken(user.email),
                UserType.Driver
            )
            else -> throw raiseException(ExceptionDictionary.USER_NOT_FOUND)
        }
        buildResultMessage(result)
    }

    private fun validateRequest(request: SignInRequest, block: (SignInRequest) -> ResponseEntity<SignInResult>) = when {
        // Check incoming parameters. If they're empty, throw an exception. This will be handled in the controller
        request.email.isEmpty() || request.password.isEmpty() ->
            throw raiseException(ExceptionDictionary.MISSING_PARAMETERS)
        // Check if password matches
        passwordMatches(request.email, request.password).not() ->
            throw raiseException(ExceptionDictionary.INVALID_CREDENTIALS)
        // All validations passed
        else -> block.invoke(request)
    }

    private fun passwordMatches(email: String, password: String): Boolean {
        val user = this.authRepository.getUserByEmail(email) ?: this.authRepository.getDriverByEmail(email)
        val storedPassword = when (user) {
            is UserEntity -> user.password
            is DriverEntity -> user.password
            else -> throw raiseException(ExceptionDictionary.USER_NOT_FOUND)
        }
        // Check if password provided matches the encoded one that's stored
        return bCryptPasswordEncoder.matches(password, storedPassword)
    }

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