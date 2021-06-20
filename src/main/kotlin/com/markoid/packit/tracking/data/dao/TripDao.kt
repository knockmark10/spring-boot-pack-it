package com.markoid.packit.tracking.data.dao

import com.markoid.packit.tracking.data.entities.TripEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface TripDao : MongoRepository<TripEntity, String> {
}