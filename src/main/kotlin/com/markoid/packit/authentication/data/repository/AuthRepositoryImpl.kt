package com.markoid.packit.authentication.data.repository

import com.markoid.packit.authentication.data.datasource.AuthDataSourceImpl
import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity

class AuthRepositoryImpl(
    private val authDataSourceImpl: AuthDataSourceImpl
) : AuthRepository {

    override fun getDriverByUserId(userId: String): DriverEntity? {
        // Fetch the driver from database, No caching is necessary since the cache is indexed by email
        return authDataSourceImpl.fetchDriverByUserId(userId)
    }

    /**
     * Gets driver by email. It will look on the cache first to avoid unnecessary look-ups, and if it's not found there,
     * it will go to the database.
     */
    override fun getDriverByEmail(email: String): DriverEntity? {
        // Get cached driver
        var driver = this.authDataSourceImpl.getCachedDriverByEmail(email)
        // If it's null, it means there is no driver cached. Fetch it from database
        if (driver == null) {
            driver = this.authDataSourceImpl.fetchDriverByEmail(email)
            driver?.let { this.authDataSourceImpl.cacheDriver(it) }
        }
        return driver
    }

    /**
     * Gets user by email. It will look on the cache first to avoid unnecessary look-ups, and if it's not found there,
     * it will go to the database.
     */
    override fun getUserByEmail(email: String): UserEntity? {
        // Get cached user
        var user = this.authDataSourceImpl.getCachedUserByEmail(email)
        // If it is null, it means there is no user cached. Fetch it from the database
        if (user == null) {
            user = this.authDataSourceImpl.fetchUserByEmail(email)
            // Cache user from database if there is one
            user?.let { this.authDataSourceImpl.cacheUser(user) }
        }
        return user
    }

    override fun getUserById(userId: String): UserEntity? {
        // Fetch the driver from database, No caching is necessary since the cache is indexed by email
        return this.authDataSourceImpl.fetchUserById(userId)
    }

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