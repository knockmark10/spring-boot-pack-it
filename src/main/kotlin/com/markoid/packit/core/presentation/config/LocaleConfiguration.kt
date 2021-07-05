package com.markoid.packit.core.presentation.config

import com.markoid.packit.core.presentation.handlers.AppLanguageResolver
import com.markoid.packit.core.presentation.utils.ApiConstants
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*
import javax.servlet.http.HttpServletRequest

@Configuration
class LocaleConfiguration : WebMvcConfigurer {

    @Bean
    fun providesLocaleResolver(messageSource: MessageSource): AppLanguageResolver = AppLanguageResolver(messageSource)

    @Bean
    fun providesLocaleChangeInterceptor(): LocaleChangeInterceptor {
        val interceptor = LocaleChangeInterceptor()
        interceptor.paramName = ApiConstants.PARAM_LANGUAGE
        return interceptor
    }

    @Bean
    fun providesAcceptHeaderLocaleResolver(mvcProperties: WebMvcProperties): AcceptHeaderLocaleResolver {
        val localeResolver = AcceptHeaderLocaleResolver()
        localeResolver.defaultLocale = Locale.US
        return localeResolver
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(providesLocaleChangeInterceptor())
    }

}