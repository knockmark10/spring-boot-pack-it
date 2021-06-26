package com.markoid.packit.core.presentation.handlers

import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.exceptions.HttpStatusException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.Instant

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        const val TIMESTAMP = "timestamp"
        const val STATUS = "status"
        const val ERRORS = "errors"
        const val TYPE = "type"
        const val PATH = "path"
        const val MESSAGE = "message"
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val validationErrors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
        return getExceptionResponseEntity(ex, status, request, validationErrors)
    }

    @ExceptionHandler(HttpStatusException::class)
    fun handleExceptions(exception: Throwable): ResponseEntity<Any> = when (exception) {
        is HttpStatusException -> {
            ResponseEntity.status(exception.status)
                .body(BaseResponse(ApiState.Error, exception.reason ?: exception.message))
        }
        else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(BaseResponse(ApiState.Error, "Server is not available at the moment."))
    }

    /**
     * Build a detailed information about the exception in the response.
     */
    private fun getExceptionResponseEntity(
        exception: Exception,
        status: HttpStatus,
        request: WebRequest,
        errors: List<String>
    ): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        val path = request.getDescription(false)
        body[TIMESTAMP] = Instant.now()
        body[STATUS] = status.value()
        body[ERRORS] = errors
        body[TYPE] = exception.javaClass.name
        body[PATH] = path
        body[MESSAGE] = "Invalid parameters provided."
        return ResponseEntity(body, status)
    }

}