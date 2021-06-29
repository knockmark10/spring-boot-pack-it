package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.tracking.data.entities.TripStatus
import javax.validation.constraints.NotBlank

data class CreateNewTripDto(

    @JsonProperty("driverId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val driverId: String,

    @JsonProperty("status", required = true)
    val tripStatus: TripStatus

)