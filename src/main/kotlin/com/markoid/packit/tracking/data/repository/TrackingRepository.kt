package com.markoid.packit.tracking.data.repository

import com.markoid.packit.tracking.data.entities.TripEntity

interface TrackingRepository {
    fun getTripById(id: String): TripEntity?
    fun saveTrip(trip: TripEntity): TripEntity
}