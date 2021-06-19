package com.markoid.packit.authentication.presentation.filters

import com.markoid.packit.authentication.domain.exceptions.AccessNotGrantedException
import com.markoid.packit.authentication.domain.exceptions.InvalidTokenException
import com.markoid.packit.core.presentation.filters.AbstractAuthenticationFilter
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.core.presentation.utils.ApiConstants.KEY
import com.markoid.packit.core.presentation.utils.ApiConstants.TOKEN_HEADER_NAME
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthorizationFilter(authManager: AuthenticationManager) : AbstractAuthenticationFilter(authManager) {

    private val endpointsWithNoRequiredToken = listOf(
        ApiConstants.SIGN_IN_URL,
        ApiConstants.SIGN_UP_URL
    )

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        // Read token from header
        val token = request.getHeader(TOKEN_HEADER_NAME)

        when {
            // There is no token, but it's a request coming from an exempt endpoint
            token == null && endpointsWithNoRequiredToken.any { request.requestURL.toString().contains(it) } ->
                chain.doFilter(request, response)

            // Token is null for endpoints with token required
            token == null -> setErrorResponse(AccessNotGrantedException(), response)

            // Authenticate existing token
            else -> {
                val authentication: UsernamePasswordAuthenticationToken? = authenticate(response, token)
                authentication?.let {
                    SecurityContextHolder.getContext().authentication = it
                    chain.doFilter(request, response)
                }
            }
        }
    }

    private fun authenticate(response: HttpServletResponse, token: String): UsernamePasswordAuthenticationToken? = try {
        val user = Jwts.parser()
            .setSigningKey(Keys.hmacShaKeyFor(KEY.toByteArray()))
            .parseClaimsJws(token)
            .body

        UsernamePasswordAuthenticationToken(user, null, emptyList())
    } catch (exception: Throwable) {
        setErrorResponse(InvalidTokenException(), response)
        null
    }

}