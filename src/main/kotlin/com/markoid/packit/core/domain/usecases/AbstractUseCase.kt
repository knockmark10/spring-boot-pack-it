package com.markoid.packit.core.domain.usecases

import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.AppLanguage
import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.exceptions.HttpStatusException
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.core.presentation.handlers.LocaleResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.server.ResponseStatusException

abstract class AbstractUseCase<Result, Params> {

    @Autowired
    private lateinit var localeResolver: LocaleResolver

    private var language: AppLanguage = AppLanguage.ENGLISH

    sealed class ValidationStatus {
        object Success : ValidationStatus()
        data class Failure(val exception: ExceptionDictionary) : ValidationStatus()
    }

    protected abstract fun onExecuteTask(params: Params): Result

    open fun onHandleValidations(params: Params): ValidationStatus = Success

    internal fun startCommand(params: Params?): Result {
        // Request nullability will be checked
        if (params == null) throw raiseException(ExceptionDictionary.MISSING_PARAMETERS)

        // Get validation result
        val validationResult = onHandleValidations(params)

        // Check if validations are successful. Otherwise, throw an error
        if (validationResult is Failure) throw raiseException(validationResult.exception)

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

    protected fun buildSuccessfulMessage(dictionary: ExceptionDictionary): ApiResult =
        ApiResult(message = localeResolver.getString(dictionary, language), status = ApiState.Success)

}