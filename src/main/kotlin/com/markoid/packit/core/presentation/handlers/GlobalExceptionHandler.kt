package com.markoid.packit.core.presentation.handlers

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.domain.exceptions.HttpStatusException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @Autowired
    private lateinit var appLanguageResolver: AppLanguageResolver

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    /**
     * Bean-validations' errors are going to be handled in here.
     */
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val validationErrors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
        val path = request.getDescription(false)
        this.logger.error("Validation error was thrown: ${validationErrors.joinToString()} at $path", ex)
        return getExceptionResponseEntity(ex, status, request, validationErrors)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return getExceptionResponseEntity(ex, status, request, listOf(ex.localizedMessage))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        exception: ConstraintViolationException,
        request: WebRequest
    ): ResponseEntity<Any> {
        val validationErrors = exception.constraintViolations.map { "${it.propertyPath}: ${it.message}" }
        return getExceptionResponseEntity(exception, HttpStatus.BAD_REQUEST, request, validationErrors)
    }

    /**
     * Custom translated exceptions are going to be handled in here.
     */
    @ExceptionHandler(HttpStatusException::class)
    fun handleHttpStatusException(
        exception: HttpStatusException,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorLog = StringBuilder()
            .append("Custom translated exception was thrown.")
            .append("\n")
            .append("Exception: $exception\tPath: ${request.getDescription(false)}")
            .append("\n")
            .toString()
        this.logger.error(errorLog, exception)
        return getExceptionResponseEntity(
            exception = exception,
            status = exception.status,
            request = request,
            message = exception.reason ?: exception.message
        )
    }

    /**
     * Rest of the exceptions will be caught in here.
     */
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<Any> {
        val responseStatus: ResponseStatus? = exception.javaClass.getAnnotation(ResponseStatus::class.java)
        val status = responseStatus?.value ?: HttpStatus.INTERNAL_SERVER_ERROR
        val localizedMessage = exception.localizedMessage
        val path = request.getDescription(false)
        val message = if (localizedMessage.isNotEmpty()) localizedMessage else status.reasonPhrase
        this.logger.error("Exception was thrown with message $message\nat uri $path")
        return getExceptionResponseEntity(
            exception = exception,
            status = status,
            request = request
        )
    }

    /**
     * Build a detailed information about the exception in the response.
     */
    private fun getExceptionResponseEntity(
        exception: Exception,
        status: HttpStatus,
        request: WebRequest,
        errors: List<String> = emptyList(),
        message: String? = null
    ): ResponseEntity<Any> {
        val path = request.getDescription(false)
        val errorBody = ApiResult(
            errors = errors,
            message = message ?: getMessageForStatus(status),
            path = path,
            status = ApiState.Error,
            type = exception.javaClass.name
        )
        return ResponseEntity(errorBody, status)
    }

    private fun getMessageForStatus(status: HttpStatus): String = when (status) {
        HttpStatus.UNAUTHORIZED ->
            this.appLanguageResolver.getString(MessageDictionary.INVALID_CREDENTIALS)
        HttpStatus.BAD_REQUEST ->
            this.appLanguageResolver.getString(MessageDictionary.MISSING_PARAMETERS)
        else -> this.appLanguageResolver.getString(MessageDictionary.SERVICE_UNAVAILABLE)
    }

}