package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.shipments.data.entities.ShipmentStatus
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class UpdateShipmentStatusDto(

    @JsonProperty("tripId", required = true)
    @NotBlank(message = "Parameter is required")
    @NotEmpty(message = "Parameter should not be empty")
    val tripId: String,

    @JsonProperty("shipId", required = true)
    @NotBlank(message = "Parameter is required")
    @NotEmpty(message = "Parameter should not be empty")
    val shipId: String,

    @JsonProperty("status", required = true)
    val shipmentStatus: ShipmentStatus

)