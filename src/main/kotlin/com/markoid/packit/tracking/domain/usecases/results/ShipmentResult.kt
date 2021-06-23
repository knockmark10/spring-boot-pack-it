package com.markoid.packit.tracking.domain.usecases.results

import com.markoid.packit.shipments.data.entities.DirectionEntity
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.entities.ShipmentStatus

data class ShipmentResult(
    val owner: String,
    val status: ShipmentStatus,
    val id: String,
    val deliveryDirection: DirectionEntity,
    val firebaseToken: String
)

