package com.markoid.packit.authentication.data.dao

import com.markoid.packit.authentication.data.entities.DriverEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface DriverDao : MongoRepository<DriverEntity, String> {
    fun getDriverByEmail(email: String): DriverEntity?
}