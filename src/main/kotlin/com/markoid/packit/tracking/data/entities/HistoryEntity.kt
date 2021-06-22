package com.markoid.packit.tracking.data.entities

import com.markoid.packit.shipments.data.entities.ShipmentStatus

data class HistoryEntity(
    val address: String,
    val city: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val shipmentStatus: ShipmentStatus,
    val state: String,
    val userId: String
) 