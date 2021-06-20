package com.markoid.packit.core.domain.usecases

import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

abstract class BaseUseCase<Result, Params>(private val messageSource: MessageSource) {

    private var language: String = "en"

    abstract fun execute(request: Params): ResponseEntity<Result>

    fun setLanguage(language: String): BaseUseCase<Result, Params> {
        this.language = language
        return this
    }

    /**
     * Gets a translated string based on the [key] provided.
     */
    fun getString(key: String): String =
        this.messageSource.getMessage(key, null, Locale.forLanguageTag(language))

    fun buildOkMessage(messageKey: String): ResponseEntity<BaseResponse> = ResponseEntity
        .status(HttpStatus.OK)
        .body(BaseResponse(ApiState.Success, getString(messageKey)))

}