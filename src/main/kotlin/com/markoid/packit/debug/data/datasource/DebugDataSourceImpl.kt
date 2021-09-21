package com.markoid.packit.debug.data.datasource

import com.markoid.packit.debug.data.dao.DebugDao
import com.markoid.packit.debug.data.entities.LogEntity

class DebugDataSourceImpl(private val debugDao: DebugDao) : DebugDataSource {

    override fun saveLogMessage(log: LogEntity) {
        this.debugDao.save(log)
    }

    override fun getLogsByUserId(userId: String): List<LogEntity> =
        this.debugDao.getLogsByUserId(userId)

}