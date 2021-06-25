package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.shipments.data.entities.ShipmentStatus

data class UpdateShipmentStatusRequest(
    val tripId: String?,
    val shipId: String?,
    @JsonProperty("status")
    val shipmentStatus: ShipmentStatus?,
)