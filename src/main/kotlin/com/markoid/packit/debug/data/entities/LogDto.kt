package com.markoid.packit.debug.data.entities

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class LogDto(

    @JsonProperty(value = "logType", required = true)
    val logType: LogType,

    @JsonProperty("tag", required = true)
    @NotBlank(message = "Parameter must not be empty")
    val tag: String,

    @JsonProperty("message", required = true)
    @NotBlank(message = "Parameter must not be empty")
    val message: String,

    @JsonProperty("instant", required = true)
    @NotBlank(message = "Parameter must not be empty")
    val instant: String,

    @JsonProperty("errorMessage", required = false)
    val errorMessage: String? = null

)

fun LogDto.toEntity(): LogEntity = LogEntity(
    logType = logType,
    tag = tag,
    message = message,
    instant = instant,
    errorMessage = errorMessage
)