package com.markoid.packit.authentication.presentation.filters


import com.fasterxml.jackson.databind.ObjectMapper
import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.presentation.utils.AuthConstants
import com.markoid.packit.authentication.presentation.utils.AuthConstants.EXPIRATION_TIME
import com.markoid.packit.authentication.presentation.utils.AuthConstants.KEY
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.security.Key
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthenticationFilter(
    private val mAuthenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication =
        try {
            val appUser = ObjectMapper().readValue(request?.inputStream, UserEntity::class.java)
            val userPasswordToken = UsernamePasswordAuthenticationToken(appUser.email, appUser.password, emptyList())
            this.mAuthenticationManager.authenticate(userPasswordToken)
        } catch (exception: Throwable) {
            throw RuntimeException()
        }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val expiration = Date(System.currentTimeMillis() + EXPIRATION_TIME)
        val key: Key = Keys.hmacShaKeyFor(KEY.toByteArray())
        val claims: Claims = Jwts.claims().setSubject((authResult?.principal as? User?)?.username)
        val token: String = Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, key)
            .setExpiration(expiration)
            .compact()
        response?.addHeader(AuthConstants.TOKEN_HEADER_NAME, token)
    }

}