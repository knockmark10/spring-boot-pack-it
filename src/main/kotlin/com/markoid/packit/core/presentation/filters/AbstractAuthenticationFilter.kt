package com.markoid.packit.core.presentation.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.data.ApiState
import com.markoid.packit.core.presentation.handlers.MessageDictionary
import com.markoid.packit.core.presentation.handlers.AppLanguageResolver
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletResponse

abstract class AbstractAuthenticationFilter(
    authManager: AuthenticationManager,
    private val appLanguageResolver: AppLanguageResolver
) : BasicAuthenticationFilter(authManager) {

    protected fun setErrorResponse(
        dictionary: MessageDictionary,
        response: HttpServletResponse
    ) {
        response.status = dictionary.statusCode.value()
        response.contentType = "application/json"
        // A class used for errors
        val apiError = ApiResult(message = appLanguageResolver.getString(dictionary), status = ApiState.Error)
        try {
            val json: String = ObjectMapper().writeValueAsString(apiError)
            response.writer.write(json)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}