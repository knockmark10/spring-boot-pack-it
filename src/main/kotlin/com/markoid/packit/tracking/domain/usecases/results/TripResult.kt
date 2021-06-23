package com.markoid.packit.tracking.domain.usecases.results

import com.markoid.packit.tracking.data.entities.TripStatus

data class TripResult(
    val tripId: String,
    val status: TripStatus,
    val shipments: List<ShipmentResult>
)