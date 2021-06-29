package com.markoid.packit.shipments.domain.usecases.requests

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class DeleteShipmentDto(

    @JsonProperty("shipId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val shipId: String,

    @JsonProperty("userId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val userId: String

)