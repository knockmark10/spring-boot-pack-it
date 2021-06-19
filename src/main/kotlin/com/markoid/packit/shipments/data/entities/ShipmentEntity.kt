package com.markoid.packit.shipments.data.entities

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
    var status: ShipmentStatus = ShipmentStatus.Idle,
    var shipId: String = "",
    @Id var id: String = "",
    var totalDistance: Double = 0.0
)

fun emptyShipment(): ShipmentEntity =
    ShipmentEntity("", DirectionEntity())