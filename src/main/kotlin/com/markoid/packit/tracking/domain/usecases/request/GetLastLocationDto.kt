package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class GetLastLocationDto(

    @JsonProperty("tripId", required = true)
    @get:NotBlank(message = "Parameter is required")
    val tripId: String,

    @JsonProperty("userId", required = true)
    @get:NotBlank(message = "Parameter is required")
    val userId: String

)