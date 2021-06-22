package com.markoid.packit.authentication.data.dao

import com.markoid.packit.authentication.data.entities.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UserDao : MongoRepository<UserEntity, String> {
    @Query("{ 'email': ?0 }")
    fun getUserByEmail(email: String): UserEntity?

    @Query("{ '_id': ?0 }")
    fun getUserById(userId: String): UserEntity?
}