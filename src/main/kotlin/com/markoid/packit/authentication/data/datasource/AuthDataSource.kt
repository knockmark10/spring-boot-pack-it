package com.markoid.packit.authentication.data.datasource

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity

interface AuthDataSource {
    fun cacheDriver(driverEntity: DriverEntity): DriverEntity
    fun cacheUser(userEntity: UserEntity): UserEntity
    fun fetchDriverByEmail(email: String): DriverEntity?
    fun fetchUserByEmail(email: String): UserEntity?
    fun getCachedDriverByEmail(email: String): DriverEntity?
    fun getCachedUserByEmail(email: String): UserEntity?
    fun saveDriverInDatabase(driverEntity: DriverEntity): DriverEntity
    fun saveUserInDatabase(userEntity: UserEntity): UserEntity
}