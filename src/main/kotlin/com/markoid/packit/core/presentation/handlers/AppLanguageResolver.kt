package com.markoid.packit.core.presentation.handlers

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import java.util.*

class AppLanguageResolver(private val messageSource: MessageSource) {

    /**
     * Gets a translated string based on the [MessageDictionary] provided.
     */
    fun getString(dictionary: MessageDictionary): String =
        this.messageSource.getMessage(dictionary.key, null, getCurrentLocale())

    /**
     * Get the current locale stored. It is based on the Accept-Language header provided.
     */
    private fun getCurrentLocale(): Locale = LocaleContextHolder.getLocale()

}