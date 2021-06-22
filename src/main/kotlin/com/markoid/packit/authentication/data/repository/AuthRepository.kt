package com.markoid.packit.authentication.data.repository

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity

interface AuthRepository {
    fun getDriverByUserId(userId: String): DriverEntity?
    fun getDriverByEmail(email: String): DriverEntity?
    fun getUserByEmail(email: String): UserEntity?
    fun getUserById(userId: String): UserEntity?
    fun saveDriver(driverEntity: DriverEntity): DriverEntity
    fun saveUser(userEntity: UserEntity): UserEntity
}