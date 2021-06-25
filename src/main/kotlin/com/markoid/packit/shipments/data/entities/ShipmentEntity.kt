package com.markoid.packit.shipments.data.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("shipments")
data class ShipmentEntity(
    var createdAt: String,
    var deliveryDirection: DirectionEntity = DirectionEntity(),
    var name: String = "",
    var owner: String = "",
    var packageList: List<PackageEntity> = mutableListOf(),
    var pickUpDirection: DirectionEntity = DirectionEntity(),
    var receivedAt: String? = null,
    @JsonProperty("id") var shipId: String = "",
    var status: ShipmentStatus = ShipmentStatus.Idle,
    @JsonProperty("shipId") @Id var id: ObjectId = ObjectId.get(),
    var totalDistance: Double = 0.0,
    var userId: String = ""
)