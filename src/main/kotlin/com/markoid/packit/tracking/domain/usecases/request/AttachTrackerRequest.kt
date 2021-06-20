package com.markoid.packit.tracking.domain.usecases.request

data class AttachTrackerRequest(
    val userId: String = "",
    val driverId: String = "",
    val shipId: String = "",
    val tripId: String = ""
)