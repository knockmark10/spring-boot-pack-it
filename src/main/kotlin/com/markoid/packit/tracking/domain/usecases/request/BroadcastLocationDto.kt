package com.markoid.packit.tracking.domain.usecases.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.shipments.data.entities.ShipmentStatus
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero

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
    val latitude: Double,

    @JsonProperty("longitude", required = true)
    val longitude: Double,

    @JsonProperty("shipmentStatus", required = true)
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