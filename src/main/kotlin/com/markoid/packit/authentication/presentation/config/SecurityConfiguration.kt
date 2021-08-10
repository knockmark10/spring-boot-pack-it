package com.markoid.packit.authentication.presentation.config

import com.markoid.packit.authentication.data.service.AppUserDetailsService
import com.markoid.packit.authentication.presentation.filters.AuthorizationFilter
import com.markoid.packit.core.presentation.handlers.AppLanguageResolver
import com.markoid.packit.core.presentation.utils.ApiConstants.AUTH_PATH
import com.markoid.packit.core.presentation.utils.ApiConstants.DEBUG_PATH
import com.markoid.packit.core.presentation.utils.ApiConstants.GET_LOG_MESSAGES_URL
import com.markoid.packit.core.presentation.utils.ApiConstants.SAVE_DEBUG_LOG_URL
import com.markoid.packit.core.presentation.utils.ApiConstants.SIGN_IN_URL
import com.markoid.packit.core.presentation.utils.ApiConstants.SIGN_UP_URL
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailsService: AppUserDetailsService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val appLanguageResolver: AppLanguageResolver
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun providesCorsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    override fun configure(http: HttpSecurity?) {
        http?.cors()?.and()?.csrf()?.disable()?.authorizeRequests()
            // Specify the endpoints that does not require token authentication
            ?.antMatchers(HttpMethod.POST, AUTH_PATH + SIGN_UP_URL)?.permitAll()
            ?.antMatchers(HttpMethod.POST, AUTH_PATH + SIGN_IN_URL)?.permitAll()
            ?.antMatchers(HttpMethod.POST, DEBUG_PATH + SAVE_DEBUG_LOG_URL)?.permitAll()
            ?.antMatchers(HttpMethod.GET, DEBUG_PATH + GET_LOG_MESSAGES_URL)?.permitAll()
            ?.anyRequest()?.authenticated()
            ?.and()
            ?.addFilter(AuthorizationFilter(authenticationManager(), appLanguageResolver))
            ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(bCryptPasswordEncoder)
    }

}