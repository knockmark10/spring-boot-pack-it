package com.markoid.packit.tracking.data.entities

import com.markoid.packit.shipments.data.entities.ShipmentStatus

data class HistoryEntity(
    val address: String = "",
    val city: String = "",
    val date: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val shipmentStatus: ShipmentStatus = ShipmentStatus.Idle,
    val state: String = "Michoacan",
    val userId: String = ""
) 