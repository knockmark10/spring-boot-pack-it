package com.markoid.packit.tracking.domain.usecases.request

data class GetLastLocationRequest(
    val tripId: String?,
    val userId: String?
)