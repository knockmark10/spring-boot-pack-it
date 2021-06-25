package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.tracking.data.entities.TripStatus

data class CreateNewTripRequest(
    val driverId: String?,
    @JsonProperty("status")
    val tripStatus: TripStatus?
)