package com.markoid.packit.tracking.data.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("trips")
data class TripEntity(
    @Field("attachments")
    private val _attachments: List<AttachmentsEntity>?,
    @Id val id: ObjectId = ObjectId.get(),
    @Field("driverId")
    private val _driverId: String?,
    @Field("status")
    private val _status: TripStatus?
) {

    val attachments: List<AttachmentsEntity>
        get() = _attachments ?: emptyList()

    val driverId: String
        get() = _driverId ?: ""

    val status: TripStatus
        get() = _status ?: TripStatus.Unknown

}