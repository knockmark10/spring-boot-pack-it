package com.markoid.packit.core.presentation.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.BaseResponse
import org.springframework.context.MessageSource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.server.ResponseStatusException
import java.io.IOException
import java.util.*
import javax.servlet.http.HttpServletResponse

abstract class AbstractAuthenticationFilter(
    authManager: AuthenticationManager,
    private val messageSource: MessageSource
) : BasicAuthenticationFilter(authManager) {

    protected fun setErrorResponse(
        exception: ResponseStatusException,
        response: HttpServletResponse
    ) {
        response.status = exception.status.value()
        response.contentType = "application/json"
        // A class used for errors
        val apiError = BaseResponse(ApiState.Error, exception.reason ?: exception.message)
        try {
            val json: String = ObjectMapper().writeValueAsString(apiError)
            response.writer.write(json)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Gets a translated string based on the [key] provided.
     */
    fun getString(key: String, language: String = "en"): String =
        this.messageSource.getMessage(key, null, Locale.forLanguageTag(language))

}