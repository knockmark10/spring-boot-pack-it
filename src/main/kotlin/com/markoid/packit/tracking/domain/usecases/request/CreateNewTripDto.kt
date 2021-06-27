package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.tracking.data.entities.TripStatus
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class CreateNewTripDto(

    @JsonProperty("driverId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter must not be empty.")
    val driverId: String,

    @JsonProperty("tripStatus", required = true)
    val tripStatus: TripStatus

)