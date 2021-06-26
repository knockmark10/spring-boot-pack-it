package com.markoid.packit.core.domain.usecases

import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.AppLanguage
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.exceptions.HttpStatusException
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.core.presentation.handlers.LocaleResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.server.ResponseStatusException

abstract class AbstractUseCase<Result, Params> {

    @Autowired
    private lateinit var localeResolver: LocaleResolver

    private var language: AppLanguage = AppLanguage.ENGLISH

    protected abstract fun onExecuteTask(params: Params): Result

    internal fun startCommand(params: Params?): Result {
        // Request nullability will be checked
        if (params == null) throw raiseException(ExceptionDictionary.MISSING_PARAMETERS)

        // Return custom implementation of use case
        return onExecuteTask(params)
    }

    /**
     * Set the [language] from the supported [AppLanguage] available. This will make sure that all the messages returned
     * by this use case will be properly translated.
     */
    internal fun setLanguage(language: String?): AbstractUseCase<Result, Params> {
        this.language = AppLanguage.forValue(language ?: "en")
        return this
    }

    /**
     * Creates a [HttpStatusException] with a translated message for the given [ExceptionDictionary].
     */
    protected fun raiseException(dictionary: ExceptionDictionary): ResponseStatusException =
        HttpStatusException(dictionary.statusCode, localeResolver.getString(dictionary, language))

    protected fun buildSuccessfulMessage(dictionary: ExceptionDictionary): BaseResponse =
        BaseResponse(ApiState.Success, localeResolver.getString(dictionary, language))

}