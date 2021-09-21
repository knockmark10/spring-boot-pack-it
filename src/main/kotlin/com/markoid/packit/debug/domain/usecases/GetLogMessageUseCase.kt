package com.markoid.packit.debug.domain.usecases

import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.debug.data.entities.LogEntity
import com.markoid.packit.debug.data.repository.DebugRepository

class GetLogMessageUseCase(
    private val debugRepository: DebugRepository
) : AbstractUseCase<List<LogEntity>, String>() {

    override fun onExecuteTask(params: String): List<LogEntity> =
        this.debugRepository.getLogsByUserId(params)

}