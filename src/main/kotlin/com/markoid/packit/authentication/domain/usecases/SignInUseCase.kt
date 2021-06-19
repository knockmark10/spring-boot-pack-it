package com.markoid.packit.authentication.domain.usecases

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.authentication.domain.exceptions.UserNotFoundException
import com.markoid.packit.authentication.domain.requests.SignInRequest
import com.markoid.packit.authentication.domain.requests.UserType
import com.markoid.packit.authentication.domain.results.SignInResult
import com.markoid.packit.authentication.presentation.utils.AuthConstants
import com.markoid.packit.core.domain.exceptions.IncompleteRequestException
import com.markoid.packit.core.domain.usecases.BaseUseCase
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.security.Key
import java.util.*


class SignInUseCase(
    private val authRepository: AuthRepository
) : BaseUseCase<SignInResult, SignInRequest>() {

    override fun execute(request: SignInRequest): ResponseEntity<SignInResult> = validateRequest(request) {
        // Search for user. If it's null, search for driver. If it doesn't exist, there is no user in the database.
        val result = when (val user =
            this.authRepository.getUserByEmail(it.email) ?: this.authRepository.getDriverByEmail(it.email)) {
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
            else -> throw UserNotFoundException()
        }
        ResponseEntity.status(HttpStatus.OK).body(result)
    }

    private fun validateRequest(request: SignInRequest, block: (SignInRequest) -> ResponseEntity<SignInResult>) = when {
        // Check incoming parameters. If they're empty, throw an exception. This will be handled in the controller
        request.email.isEmpty() || request.password.isEmpty() -> throw IncompleteRequestException()
        else -> block.invoke(request)
    }

    private fun generateJsonWebToken(username: String): String {
        val expiration = Date(System.currentTimeMillis() + AuthConstants.EXPIRATION_TIME)
        val claims: Claims = Jwts.claims().setSubject(username)
        val key: Key = Keys.hmacShaKeyFor(AuthConstants.KEY.toByteArray())
        return try {
            Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiration)
                .compact()
        } catch (exception: Throwable) {
            exception.printStackTrace()
            ""
        }
    }

}