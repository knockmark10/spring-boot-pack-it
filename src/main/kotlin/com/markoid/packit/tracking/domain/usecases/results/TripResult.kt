package com.markoid.packit.tracking.domain.usecases.results

import com.fasterxml.jackson.annotation.JsonInclude
import com.markoid.packit.tracking.data.entities.TripStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TripResult(
    val tripId: String? = null,
    val status: TripStatus? = null,
    val shipments: List<ShipmentResult>? = null
)