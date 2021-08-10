package com.markoid.packit.debug.data.entities

import javax.validation.constraints.NotBlank

data class LogDto(

    @NotBlank(message = "Parameter must not be empty")
    val userId: String,

    val logType: LogType,

    @NotBlank(message = "Parameter must not be empty")
    val tag: String,

    @NotBlank(message = "Parameter must not be empty")
    val message: String,

    @NotBlank(message = "Parameter must not be empty")
    val instant: String,

    val errorMessage: String? = null

)

fun LogDto.toEntity(): LogEntity = LogEntity(
    userId = userId,
    logType = logType,
    tag = tag,
    message = message,
    instant = instant,
    errorMessage = errorMessage
)