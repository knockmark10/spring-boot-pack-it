package com.markoid.packit.tracking.domain.usecases.results

data class GetAttachedTripResult(
    val tripId: String,
    val shipId: String
)