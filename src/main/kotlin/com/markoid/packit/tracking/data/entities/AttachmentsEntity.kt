package com.markoid.packit.tracking.data.entities

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.debug.data.entities.LogEntity
import org.springframework.data.mongodb.core.mapping.Field

data class AttachmentsEntity(
    @JsonProperty("shipId")
    @Field("shipId")
    val shipmentId: String,
    @Field("userId")
    val userId: String,
    @JsonProperty("history")
    @Field("history")
    val historyList: MutableList<HistoryEntity> = mutableListOf(),
    val logs: MutableList<LogEntity> = mutableListOf()
)