package com.markoid.packit.authentication.presentation.filters

import com.markoid.packit.authentication.domain.exceptions.AccessNotGrantedException
import com.markoid.packit.authentication.domain.exceptions.InvalidTokenException
import com.markoid.packit.authentication.domain.exceptions.UserNotFoundException
import com.markoid.packit.authentication.presentation.utils.AuthConstants.KEY
import com.markoid.packit.authentication.presentation.utils.AuthConstants.TOKEN_HEADER_NAME
import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        // Read token from header
        val token = request.getHeader(TOKEN_HEADER_NAME)

        if (token == null) {
            chain.doFilter(request, response)
            return
        }

        val authentication: UsernamePasswordAuthenticationToken? = authenticate(token)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun authenticate(token: String): UsernamePasswordAuthenticationToken? {
        val user = Jwts.parser()
            .setSigningKey(Keys.hmacShaKeyFor(KEY.toByteArray()))
            .parseClaimsJws(token)
            .body

        return UsernamePasswordAuthenticationToken(user, null, emptyList())
    }

}