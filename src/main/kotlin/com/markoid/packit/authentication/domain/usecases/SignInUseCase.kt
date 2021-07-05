package com.markoid.packit.authentication.domain.usecases

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.authentication.domain.requests.SignInEntityDto
import com.markoid.packit.authentication.domain.requests.UserType
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.core.presentation.utils.ApiConstants
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.security.Key
import java.util.*

class SignInUseCase(
    private val authRepository: AuthRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : AbstractUseCase<SignInResult, SignInEntityDto>() {

    override fun onExecuteTask(params: SignInEntityDto): SignInResult {
        // Search for user or driver. If it's not found, throw an error
        val user = getUserAndUpdateFirebaseToken(params.email, params.firebaseToken)
            ?: getDriverAndUpdateFirebaseToken(params.email, params.firebaseToken)
            ?: throw raiseException(ExceptionDictionary.USER_NOT_FOUND)

        // Check if password provided matches with the one stored in the database
        if (passwordMatches(params.password, user.password).not())
            throw raiseException(ExceptionDictionary.INVALID_CREDENTIALS)

        // Create a result object
        return SignInResult(
            id = user.id.toHexString(),
            name = user.name,
            lastName = user.lastName,
            token = generateJsonWebToken(user.email),
            userType = if (user is UserEntity) UserType.User else UserType.Driver
        )
    }

    private fun getUserAndUpdateFirebaseToken(email: String, firebaseToken: String): UserEntity? =
        this.authRepository.getUserByEmail(email)?.let {
            // If user is found, update firebase token ONLY if it's not empty.
            if (firebaseToken.isNotEmpty()) this.authRepository.saveUser(it.copy(firebaseToken = firebaseToken))
            else it
        }

    private fun getDriverAndUpdateFirebaseToken(email: String, firebaseToken: String): DriverEntity? =
        this.authRepository.getDriverByEmail(email)?.let {
            // If Driver is found, update firebase token ONLY if it's not empty.
            if (firebaseToken.isNotEmpty()) this.authRepository.saveDriver(it.copy(firebaseToken = firebaseToken))
            else it
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