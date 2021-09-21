package com.markoid.packit.debug.data.dao

import com.markoid.packit.debug.data.entities.LogEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface DebugDao : MongoRepository<LogEntity, String> {
    @Query("{ 'userId': $0 }")
    fun getLogsByUserId(userId: String): List<LogEntity>
}