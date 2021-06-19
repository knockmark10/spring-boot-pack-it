package com.markoid.packit.authentication.data.repository

import com.markoid.packit.authentication.data.datasource.AuthDataSourceImpl
import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity

class AuthRepositoryImpl(
    private val authDataSourceImpl: AuthDataSourceImpl
) : AuthRepository {

    /**
     * Gets driver by email. It will look on the cache first to avoid unnecessary look-ups, and if it's not found there,
     * it will go to the database.
     */
    override fun getDriverByEmail(email: String): DriverEntity? =
        this.authDataSourceImpl.getCachedDriverByEmail(email) ?: this.authDataSourceImpl.fetchDriverByEmail(email)

    /**
     * Gets user by email. It will look on the cache first to avoid unnecessary look-ups, and if it's not found there,
     * it will go to the database.
     */
    override fun getUserByEmail(email: String): UserEntity? =
        this.authDataSourceImpl.getCachedUserByEmail(email) ?: this.authDataSourceImpl.fetchUserByEmail(email)

    override fun saveDriver(driverEntity: DriverEntity): DriverEntity {
        // Save driver on database
        this.authDataSourceImpl.saveDriverInDatabase(driverEntity)
        // Cache it
        this.authDataSourceImpl.cacheDriver(driverEntity)
        // Return provided driver
        return driverEntity
    }

    override fun saveUser(userEntity: UserEntity): UserEntity {
        // Save user on database
        this.authDataSourceImpl.saveUserInDatabase(userEntity)
        // Cache it
        this.authDataSourceImpl.cacheUser(userEntity)
        // Return provided driver
        return userEntity
    }

}