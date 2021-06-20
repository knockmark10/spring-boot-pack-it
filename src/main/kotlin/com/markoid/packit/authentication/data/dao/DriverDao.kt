package com.markoid.packit.authentication.data.dao

import com.markoid.packit.authentication.data.entities.DriverEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface DriverDao : MongoRepository<DriverEntity, String> {

    @Query("{ 'email': ?0 }")
    fun getDriverByEmail(email: String): DriverEntity?

    @Query("{ 'userId': ?0 }")
    fun getDriverByUserId(userId: String): DriverEntity?

}