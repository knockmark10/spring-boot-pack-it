package com.markoid.packit.tracking.data.repository

import com.markoid.packit.tracking.data.datasource.TrackingDataSourceImpl
import com.markoid.packit.tracking.data.entities.TripEntity

class TrackingRepositoryImpl(
    private val trackingDataSourceImpl: TrackingDataSourceImpl
) : TrackingRepository {

    override fun getTripById(id: String): TripEntity? {
        return this.trackingDataSourceImpl.fetchTripById(id)
    }

    override fun getTripByDriverId(driverId: String): TripEntity? {
        return this.trackingDataSourceImpl.fetchTripByDriverId(driverId)
    }

    override fun saveTrip(trip: TripEntity): TripEntity {
        return this.trackingDataSourceImpl.createNewTrip(trip)
    }

}