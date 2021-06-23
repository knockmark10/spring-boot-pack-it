package com.markoid.packit.tracking.data.datasource

import com.markoid.packit.tracking.data.dao.TripDao
import com.markoid.packit.tracking.data.entities.TripEntity

class TrackingDataSourceImpl(
    private val tripDao: TripDao
) : TrackingDataSource {

    override fun createNewTrip(trip: TripEntity): TripEntity {
        return this.tripDao.save(trip)
    }

    override fun fetchTripByDriverId(driverId: String): TripEntity? {
        return this.tripDao.getTripByDriverId(driverId)
    }

    override fun fetchTripById(tripId: String): TripEntity? {
        return this.tripDao.getTripById(tripId)
    }

}