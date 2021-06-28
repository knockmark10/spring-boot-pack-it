package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.shipments.data.entities.ShipmentStatus
import com.markoid.packit.shipments.presentation.annotations.ValidShipmentStatus
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class BroadcastLocationDto(

    @JsonProperty("address", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val address: String = "",

    @JsonProperty("city", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val city: String,

    @JsonProperty("date", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val date: String,

    @JsonProperty("latitude", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val latitude: Double,

    @JsonProperty("longitude", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val longitude: Double,

    @JsonProperty("shipmentStatus", required = true)
    @get:ValidShipmentStatus(message = "Parameter is not a valid entry from ShipmentStatus.")
    val shipmentStatus: ShipmentStatus,

    @JsonProperty("state", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val state: String,

    @JsonProperty("shipId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val shipId: String,

    @JsonProperty("tripId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val tripId: String,

    @JsonProperty("userId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    val userId: String

)