package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class AttachTrackerDto(

    @JsonProperty("userId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val userId: String,

    @JsonProperty("driverId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val driverId: String,

    @JsonProperty("shipId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val shipId: String,

    @JsonProperty("tripId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val tripId: String

)