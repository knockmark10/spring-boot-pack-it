package com.markoid.packit.core.presentation.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.data.AppLanguage
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.core.presentation.handlers.LocaleResolver
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletResponse

abstract class AbstractAuthenticationFilter(
    authManager: AuthenticationManager,
    private val localeResolver: LocaleResolver
) : BasicAuthenticationFilter(authManager) {

    protected fun setErrorResponse(
        dictionary: ExceptionDictionary,
        response: HttpServletResponse,
        language: AppLanguage
    ) {
        response.status = dictionary.statusCode.value()
        response.contentType = "application/json"
        // A class used for errors
        val apiError = ApiResult(message = localeResolver.getString(dictionary, language), status = ApiState.Error)
        try {
            val json: String = ObjectMapper().writeValueAsString(apiError)
            response.writer.write(json)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}