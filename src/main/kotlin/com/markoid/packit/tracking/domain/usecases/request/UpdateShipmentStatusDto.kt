package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.shipments.data.entities.ShipmentStatus
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class UpdateShipmentStatusDto(

    @JsonProperty("tripId", required = true)
    @get:NotBlank(message = "Parameter is required")
    val tripId: String,

    @JsonProperty("shipId", required = true)
    @get:NotBlank(message = "Parameter is required")
    val shipId: String,

    @JsonProperty("status", required = true)
    val shipmentStatus: ShipmentStatus

)