package com.markoid.packit.authentication.presentation.filters

import com.markoid.packit.core.data.AppLanguage
import com.markoid.packit.core.presentation.filters.AbstractAuthenticationFilter
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.core.presentation.handlers.LocaleResolver
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.core.presentation.utils.ApiConstants.HEADER_LANGUAGE
import com.markoid.packit.core.presentation.utils.ApiConstants.HEADER_TOKEN
import com.markoid.packit.core.presentation.utils.ApiConstants.KEY
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    authManager: AuthenticationManager,
    localeResolver: LocaleResolver
) : AbstractAuthenticationFilter(authManager, localeResolver) {

    private val endpointsWithNoRequiredToken = listOf(
        ApiConstants.SIGN_IN_URL,
        ApiConstants.SIGN_UP_URL
    )

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        // Read token from header
        val token = request.getHeader(HEADER_TOKEN)
        // Read language from header
        val headerLanguage = request.getHeader(HEADER_LANGUAGE) ?: "en"
        // Resolved language
        val language = AppLanguage.forValue(headerLanguage)

        when {
            // There is no token, but it's a request coming from an exempt endpoint, then let it go through
            token == null && endpointsWithNoRequiredToken.any { request.requestURL.toString().contains(it) } ->
                chain.doFilter(request, response)

            // Token is null for endpoints with token required, then send custom error
            token == null -> setErrorResponse(ExceptionDictionary.ACCESS_NOT_GRANTED, response, language)

            // Authenticate existing token. It will send error if token has expired or is invalid.
            else -> authenticate(response, token, language)?.let {
                SecurityContextHolder.getContext().authentication = it
                chain.doFilter(request, response)
            }
        }
    }

    private fun authenticate(
        response: HttpServletResponse,
        token: String,
        language: AppLanguage
    ): UsernamePasswordAuthenticationToken? = try {
        val user = Jwts.parser()
            .setSigningKey(Keys.hmacShaKeyFor(KEY.toByteArray()))
            .parseClaimsJws(token)
            .body

        UsernamePasswordAuthenticationToken(user, null, emptyList())
    } catch (exception: Throwable) {
        // If token is invalid, send error message
        setErrorResponse(ExceptionDictionary.TOKEN_FAILED, response, language)
        null
    }

}