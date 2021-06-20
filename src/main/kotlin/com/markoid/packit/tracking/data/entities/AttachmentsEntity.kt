package com.markoid.packit.tracking.data.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Field

data class AttachmentsEntity(
    @JsonProperty("shipId")
    @Field("shipId")
    private val _shipmentId: String?,
    @Field("userId")
    private val _userId: String?,
    @JsonProperty("history")
    @Field("history")
    private val _historyList: List<HistoryEntity>?
) {

    val shipmentId: String
        get() = _shipmentId ?: ""

    val userId: String
        get() = _userId ?: ""

    val historyList: List<HistoryEntity>
        get() = _historyList ?: emptyList()

}