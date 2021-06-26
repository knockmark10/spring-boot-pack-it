package com.markoid.packit.core.domain.usecases

import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.AppLanguage
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.exceptions.HttpStatusException
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.core.presentation.handlers.LocaleResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException

@Deprecated("This use case performs HTTP routing, when it shouldn't. AbstractUseCase will be used from now on.")
abstract class BaseUseCase<Result, Params> {

    @Autowired
    private lateinit var localeResolver: LocaleResolver

    private var language: AppLanguage = AppLanguage.ENGLISH

    sealed class ValidationStatus {
        object Success : ValidationStatus()
        data class Failure(val exception: ExceptionDictionary) : ValidationStatus()
    }

    /**
     * This will be executed after [onValidateRequest] has been called. This will ensure that the [request] object
     * will be validated properly.
     */
    protected abstract fun postValidatedExecution(request: Params): Result

    /**
     * This method will be called before [startCommand], and it will be used to validate the request passed to the
     * use case. Take advantage of this method as you can perform all the custom validations needed.
     *
     * @param request -> The object that will be taken care of by use case
     * @return [ValidationStatus.Success] - When all validations passed.
     * @return [ValidationStatus.Failure] - When the request didn't pass the validation.
     *
     */
    open fun onValidateRequest(request: Params): ValidationStatus = ValidationStatus.Success

    /**
     * Call this method when you want to start the use case execution. This will make sure to validate the [request]
     * and raise proper exceptions when some validations fail the process.
     */
    internal fun startCommand(request: Params?): ResponseEntity<Result> = try {
        // Request nullability will be checked
        if (request == null) throw raiseException(ExceptionDictionary.MISSING_PARAMETERS)

        // Get the validation result. It will be 'Success' by default
        val validationStatus = onValidateRequest(request)

        // If it is 'Failure', throw the exception wrapped
        if (validationStatus is ValidationStatus.Failure) throw raiseException(validationStatus.exception)

        // Validation succeeded. We can proceed to execute the use case. Wrap the return object into a response entity.
        buildResultMessage(postValidatedExecution(request))

    } catch (exception: Throwable) {
        // We need to filter out HTTP status exceptions. If we receive one, we must throw it as received.
        throw if (exception is HttpStatusException) exception

        // Otherwise [HttpStatus.INTERNAL_SERVER_ERROR] will be thrown.
        else raiseException(ExceptionDictionary.SERVICE_UNAVAILABLE)
    }

    /**
     * Set the [language] from the supported [AppLanguage] available. This will make sure that all the messages returned
     * by this use case will be properly translated.
     */
    internal fun setLanguage(language: String?): BaseUseCase<Result, Params> {
        this.language = AppLanguage.forValue(language ?: "en")
        return this
    }

    protected fun buildOkMessage(dictionary: ExceptionDictionary): BaseResponse =
        BaseResponse(ApiState.Success, localeResolver.getString(dictionary, language))

    /**
     * Creates a [HttpStatusException] with a translated message for the given [ExceptionDictionary].
     */
    protected fun raiseException(dictionary: ExceptionDictionary): ResponseStatusException =
        HttpStatusException(dictionary.statusCode, localeResolver.getString(dictionary, language))

    private fun buildResultMessage(result: Result): ResponseEntity<Result> = ResponseEntity
        .status(HttpStatus.OK)
        .body(result)

}