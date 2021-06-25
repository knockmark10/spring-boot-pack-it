package com.markoid.packit.shipments.data.entities

import com.fasterxml.jackson.annotation.JsonProperty
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

    @Id
    @JsonProperty("shipId")
    var shipId: String = "",

    var status: ShipmentStatus = ShipmentStatus.Idle,

    var totalDistance: Double = 0.0,

    var userId: String = ""

)