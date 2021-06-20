package com.markoid.packit.tracking.data.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("trips")
data class TripEntity(
    @Field("attachments")
    val attachments: List<AttachmentsEntity>? = emptyList(),
    @Id val id: ObjectId = ObjectId.get(),
    @Field("driverId")
    val driverId: String? = "",
    @Field("status")
    val status: TripStatus? = TripStatus.Unknown
) 