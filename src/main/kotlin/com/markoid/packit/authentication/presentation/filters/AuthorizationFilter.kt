package com.markoid.packit.authentication.presentation.filters

import com.markoid.packit.core.presentation.utils.ApiConstants.KEY
import com.markoid.packit.core.presentation.utils.ApiConstants.TOKEN_HEADER_NAME
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
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