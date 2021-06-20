package com.markoid.packit.core.presentation.config

import com.markoid.packit.core.presentation.handlers.LocaleResolver
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LocaleConfiguration {

    @Bean
    fun providesLocaleResolver(messageSource: MessageSource): LocaleResolver = LocaleResolver(messageSource)

}