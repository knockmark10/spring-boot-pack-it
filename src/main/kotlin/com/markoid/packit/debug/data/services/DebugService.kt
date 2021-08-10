package com.markoid.packit.debug.data.services

import com.markoid.packit.debug.data.entities.LogDto
import com.markoid.packit.debug.data.entities.LogEntity

interface DebugService {
    fun saveLogMessage(log: LogDto)
    fun getLogMessagesByUserId(userId: String?): List<LogEntity>
}