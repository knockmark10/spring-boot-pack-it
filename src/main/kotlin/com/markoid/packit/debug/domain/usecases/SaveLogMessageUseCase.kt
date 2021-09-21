package com.markoid.packit.debug.domain.usecases

import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.debug.data.entities.LogDto
import com.markoid.packit.debug.data.repository.DebugRepository

class SaveLogMessageUseCase(
    private val debugRepository: DebugRepository
) : AbstractUseCase<Unit, LogDto>() {

    override fun onExecuteTask(params: LogDto) {
        // Save log message
        this.debugRepository.saveLogMessage(params)
    }

}