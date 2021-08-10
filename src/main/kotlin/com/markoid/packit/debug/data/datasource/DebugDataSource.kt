package com.markoid.packit.debug.data.datasource

import com.markoid.packit.debug.data.entities.LogEntity

interface DebugDataSource {
    fun saveLogMessage(log: LogEntity)
    fun getLogsByUserId(userId: String): List<LogEntity>
}