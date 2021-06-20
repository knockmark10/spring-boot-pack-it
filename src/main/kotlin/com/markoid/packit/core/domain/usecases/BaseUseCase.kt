package com.markoid.packit.core.domain.usecases

import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.exceptions.HttpStatusException
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.core.presentation.handlers.LocaleResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException

abstract class BaseUseCase<Result, Params> {

    @Autowired
    private lateinit var localeResolver: LocaleResolver

    private var language: String = "en"

    abstract fun execute(request: Params): ResponseEntity<Result>

    fun setLanguage(language: String): BaseUseCase<Result, Params> {
        this.language = language
        return this
    }

    fun buildOkMessage(dictionary: ExceptionDictionary): ResponseEntity<BaseResponse> = ResponseEntity
        .status(dictionary.statusCode)
        .body(BaseResponse(ApiState.Success, localeResolver.getString(dictionary, language)))

    /**
     * Creates a [HttpStatusException] with a translated message for the given [ExceptionDictionary].
     */
    fun raiseException(dictionary: ExceptionDictionary): ResponseStatusException =
        HttpStatusException(dictionary.statusCode, localeResolver.getString(dictionary, language))

}