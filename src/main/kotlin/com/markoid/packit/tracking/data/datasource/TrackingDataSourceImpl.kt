package com.markoid.packit.tracking.data.datasource

import com.markoid.packit.tracking.data.dao.TripDao
import com.markoid.packit.tracking.data.entities.TripEntity

class TrackingDataSourceImpl(
    val tripDao: TripDao
) : TrackingDataSource {

    override fun createNewTrip(trip: TripEntity): TripEntity {
        return this.tripDao.save(trip)
    }

}