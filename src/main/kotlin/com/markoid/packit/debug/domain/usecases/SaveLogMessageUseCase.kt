package com.markoid.packit.debug.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.MessageDictionary
import com.markoid.packit.debug.data.entities.LogDto
import com.markoid.packit.debug.data.repository.DebugRepository

class SaveLogMessageUseCase(
    private val authRepository: AuthRepository,
    private val debugRepository: DebugRepository
) : AbstractUseCase<Unit, LogDto>() {

    override fun onExecuteTask(params: LogDto) {
        // Validate that the user exists
        this.authRepository.getUserById(params.userId)
            ?: this.authRepository.getDriverByUserId(params.userId)
            ?: throw raiseException(MessageDictionary.DRIVER_NOT_FOUND)

        // Save log message
        this.debugRepository.saveLogMessage(params)
    }

}