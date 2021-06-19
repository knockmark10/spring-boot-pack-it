package com.markoid.packit.authentication.data.dao

import com.markoid.packit.authentication.data.entities.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDao : MongoRepository<UserEntity, String> {
    fun getUserByEmail(email: String): UserEntity?
}