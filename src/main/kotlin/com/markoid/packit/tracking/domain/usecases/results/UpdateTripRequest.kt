package com.markoid.packit.tracking.domain.usecases.results

import com.markoid.packit.tracking.data.entities.TripStatus

data class UpdateTripRequest(
    val tripId: String?,
    val status: TripStatus?,
    val endTime: String?
)