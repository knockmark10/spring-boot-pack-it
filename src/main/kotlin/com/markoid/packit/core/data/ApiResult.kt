package com.markoid.packit.core.data

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

data class ApiResult(

    /**
     * Message in human-readable format so that user knows what is going on.
     */
    val message: String,


    /**
     * Status of the response. It corresponds to [ApiState]
     */
    val status: ApiState,

    /**
     * List of errors encountered. Only visible if list is not empty.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val errors: List<String> = emptyList(),

    /**
     * Path of API in which the result is being produced.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val path: String = "",

    /**
     * Timestamp in which the response was produced.
     */
    val timestamp: String = Instant.now().toString(),

    /**
     * In case of error, the type of the exception thrown. Only visible if property is not empty.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val type: String = ""

)