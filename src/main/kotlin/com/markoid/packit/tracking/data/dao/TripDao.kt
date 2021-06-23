package com.markoid.packit.tracking.data.dao

import com.markoid.packit.tracking.data.entities.TripEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface TripDao : MongoRepository<TripEntity, String> {
    @Query("{ '_id': ?0 }")
    fun getTripById(tripId: String): TripEntity?

    @Query("{ 'driverId': ?0 }")
    fun getTripByDriverId(driverId: String): TripEntity?
}