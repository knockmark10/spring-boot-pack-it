package com.markoid.packit.tracking.domain.usecases.results

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.tracking.data.entities.TripStatus
import javax.validation.constraints.NotBlank

data class UpdateTripDto(

    @JsonProperty("tripId", required = true)
    @get:NotBlank(message = "Parameter is required")
    val tripId: String,

    @JsonProperty("status", required = true)
    val status: TripStatus,

    @JsonProperty("endTime", required = false)
    val endTime: String?

)