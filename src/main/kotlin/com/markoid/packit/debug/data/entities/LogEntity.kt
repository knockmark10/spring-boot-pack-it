package com.markoid.packit.debug.data.entities

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("logs")
data class LogEntity(

    @Field("type")
    val logType: LogType,

    @Field("tag")
    val tag: String,

    @Field("message")
    val message: String,

    @Field("instant")
    val instant: String,

    @Field("error")
    val errorMessage: String? = null

)