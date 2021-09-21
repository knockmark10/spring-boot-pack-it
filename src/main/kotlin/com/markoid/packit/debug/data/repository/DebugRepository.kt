package com.markoid.packit.debug.data.repository

import com.markoid.packit.debug.data.entities.LogDto
import com.markoid.packit.debug.data.entities.LogEntity

interface DebugRepository {
    fun saveLogMessage(log: LogDto)
    fun getLogsByUserId(userId: String): List<LogEntity>
}