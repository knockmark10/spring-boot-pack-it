package com.markoid.packit.shipments.data.entities

data class DirectionEntity(
    val address: String = "",
    val city: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val state: String = "",
)