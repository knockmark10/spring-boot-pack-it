package com.markoid.packit.tracking.domain.usecases.request

import com.markoid.packit.tracking.data.entities.TripStatus

data class CreateNewTripRequest(
    val userId: String?,
    val driverId: String?,
    val tripStatus: TripStatus?
)