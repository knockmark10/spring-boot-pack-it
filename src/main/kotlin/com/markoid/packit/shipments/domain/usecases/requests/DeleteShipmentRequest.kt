package com.markoid.packit.shipments.domain.usecases.requests

data class DeleteShipmentRequest(
    val shipId: String?,
    val userId: String?
)