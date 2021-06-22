package com.markoid.packit.core.presentation.handlers

import com.markoid.packit.core.data.AppLanguage
import org.springframework.context.MessageSource
import java.util.*

class LocaleResolver(private val messageSource: MessageSource) {

    /**
     * Gets a translated string based on the [ExceptionDictionary] provided.
     */
    fun getString(dictionary: ExceptionDictionary, language: AppLanguage): String =
        this.messageSource.getMessage(dictionary.key, null, Locale.forLanguageTag(language.value))

}