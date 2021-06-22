package com.markoid.packit.authentication.data.datasource

import com.markoid.packit.authentication.data.cache.DriverCacheImpl
import com.markoid.packit.authentication.data.cache.UserCacheImpl
import com.markoid.packit.authentication.data.dao.DriverDao
import com.markoid.packit.authentication.data.dao.UserDao
import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.authentication.data.entities.UserEntity

class AuthDataSourceImpl(
    private val driverCache: DriverCacheImpl,
    private val driverDao: DriverDao,
    private val userCache: UserCacheImpl,
    private val userDao: UserDao
) : AuthDataSource {

    override fun cacheDriver(driverEntity: DriverEntity): DriverEntity {
        return this.driverCache.saveOrUpdate(driverEntity.email, driverEntity)
    }

    override fun cacheUser(userEntity: UserEntity): UserEntity {
        return this.userCache.saveOrUpdate(userEntity.email, userEntity)
    }

    override fun fetchDriverByUserId(userId: String): DriverEntity? {
        return this.driverDao.getDriverByUserId(userId)
    }

    override fun fetchDriverByEmail(email: String): DriverEntity? {
        return this.driverDao.getDriverByEmail(email)
    }

    override fun fetchUserByEmail(email: String): UserEntity? {
        return this.userDao.getUserByEmail(email)
    }

    override fun fetchUserById(userId: String): UserEntity? {
        return this.userDao.getUserById(userId)
    }

    override fun getCachedDriverByEmail(email: String): DriverEntity? {
        return this.driverCache.getById(email)
    }

    override fun getCachedUserByEmail(email: String): UserEntity? {
        return userCache.getById(email)
    }

    override fun saveDriverInDatabase(driverEntity: DriverEntity): DriverEntity {
        this.driverDao.save(driverEntity)
        return driverEntity
    }

    override fun saveUserInDatabase(userEntity: UserEntity): UserEntity {
        this.userDao.save(userEntity)
        return userEntity
    }

}