package com.markoid.packit.tracking.data.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Field

data class AttachmentsEntity(
    @JsonProperty("shipId")
    @Field("shipId")
    val shipmentId: String? = null,
    @Field("userId")
    val userId: String? = null,
    @JsonProperty("history")
    @Field("history")
    val historyList: MutableList<HistoryEntity> = mutableListOf()
)