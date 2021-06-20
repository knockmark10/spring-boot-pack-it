package com.markoid.packit.tracking.data.repository

import com.markoid.packit.tracking.data.datasource.TrackingDataSourceImpl
import com.markoid.packit.tracking.data.entities.TripEntity

class TrackingRepositoryImpl(
    private val trackingDataSourceImpl: TrackingDataSourceImpl
) : TrackingRepository {

    override fun saveTrip(trip: TripEntity): TripEntity {
        return this.trackingDataSourceImpl.createNewTrip(trip)
    }

}