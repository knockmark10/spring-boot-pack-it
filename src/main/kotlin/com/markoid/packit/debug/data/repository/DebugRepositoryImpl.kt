package com.markoid.packit.debug.data.repository

import com.markoid.packit.debug.data.datasource.DebugDataSourceImpl
import com.markoid.packit.debug.data.entities.LogDto
import com.markoid.packit.debug.data.entities.LogEntity
import com.markoid.packit.debug.data.entities.toEntity

class DebugRepositoryImpl(
    private val debugDataSource: DebugDataSourceImpl
) : DebugRepository {

    override fun saveLogMessage(log: LogDto) {
        this.debugDataSource.saveLogMessage(log.toEntity())
    }

    override fun getLogsByUserId(userId: String): List<LogEntity> =
        this.debugDataSource.getLogsByUserId(userId)

}