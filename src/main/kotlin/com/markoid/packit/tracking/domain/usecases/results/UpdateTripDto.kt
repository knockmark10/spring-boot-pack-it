package com.markoid.packit.tracking.domain.usecases.results

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.tracking.data.entities.TripStatus
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class UpdateTripDto(

    @JsonProperty("tripId", required = true)
    @NotBlank(message = "Parameter is required")
    @NotEmpty(message = "Parameter should not be empty")
    val tripId: String,

    @JsonProperty("status", required = true)
    val status: TripStatus,

    @JsonProperty("endTime", required = false)
    val endTime: String?

)