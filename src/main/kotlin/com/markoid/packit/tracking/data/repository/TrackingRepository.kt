package com.markoid.packit.tracking.data.repository

import com.markoid.packit.tracking.data.entities.TripEntity

interface TrackingRepository {
    fun saveTrip(trip: TripEntity): TripEntity
}