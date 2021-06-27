package com.markoid.packit.shipments.domain.usecases.requests

import com.fasterxml.jackson.annotation.JsonProperty
import com.markoid.packit.shipments.data.entities.DirectionEntity
import com.markoid.packit.shipments.data.entities.PackageEntity
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.entities.ShipmentStatus
import com.markoid.packit.shipments.presentation.annotations.ValidDirection
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero

data class ShipmentEntityDto(

    @JsonProperty("createdAt", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter should not be empty.")
    val createdAt: String,

    @JsonProperty("deliveryDirection", required = true)
    @get:ValidDirection(message = "Parameter is invalid.")
    val deliveryDirection: DirectionEntity,

    @JsonProperty("name", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter should not be empty.")
    val name: String,

    @JsonProperty("owner", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter should not be empty.")
    val owner: String,

    @JsonProperty("packageList")
    val packageList: List<PackageEntity> = mutableListOf(),

    @JsonProperty("pickUpDirection", required = true)
    @get:ValidDirection(message = "Parameter is invalid.")
    val pickUpDirection: DirectionEntity,

    @JsonProperty("receivedAt", required = false)
    val receivedAt: String?,

    @JsonProperty("shipId", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter should not be empty.")
    val shipId: String,

    @JsonProperty("status", required = true)
    val status: ShipmentStatus,

    @JsonProperty("totalDistance", required = true)
    @get:PositiveOrZero(message = "Parameter must be positive or zero.")
    val totalDistance: Double,

    @JsonProperty("userId")
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter should not be empty.")
    val userId: String = ""

)

/**
 * Custom mapper extension function to map DTO to entity.
 */
fun ShipmentEntityDto.mapToEntity(): ShipmentEntity = ShipmentEntity(
    createdAt = createdAt,
    deliveryDirection = deliveryDirection,
    name = name,
    owner = owner,
    packageList = packageList,
    pickUpDirection = pickUpDirection,
    receivedAt = receivedAt,
    shipId = shipId,
    status = status,
    totalDistance = totalDistance,
    userId = userId
)