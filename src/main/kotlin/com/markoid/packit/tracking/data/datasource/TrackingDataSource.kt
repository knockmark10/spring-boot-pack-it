package com.markoid.packit.tracking.data.datasource

import com.markoid.packit.tracking.data.entities.TripEntity

interface TrackingDataSource {
    fun createNewTrip(trip: TripEntity): TripEntity
}