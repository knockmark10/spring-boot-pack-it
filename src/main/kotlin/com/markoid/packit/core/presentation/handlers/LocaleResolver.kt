package com.markoid.packit.core.presentation.handlers

import org.springframework.context.MessageSource
import java.util.*

class LocaleResolver(private val messageSource: MessageSource) {

    /**
     * Gets a translated string based on the [ExceptionDictionary] provided.
     */
    fun getString(dictionary: ExceptionDictionary, language: String = "en"): String =
        this.messageSource.getMessage(dictionary.key, null, Locale.forLanguageTag(language))

}