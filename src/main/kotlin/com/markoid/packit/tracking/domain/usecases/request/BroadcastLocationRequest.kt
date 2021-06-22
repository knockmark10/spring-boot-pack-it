package com.markoid.packit.tracking.domain.usecases.request

import com.markoid.packit.shipments.data.entities.ShipmentStatus

data class BroadcastLocationRequest(
    val address: String? = "",
    val city: String?,
    val date: String?,
    val latitude: Double?,
    val longitude: Double?,
    val shipmentStatus: ShipmentStatus?,
    val state: String?,
    val shipId: String?,
    val tripId: String?,
    val userId: String?
)