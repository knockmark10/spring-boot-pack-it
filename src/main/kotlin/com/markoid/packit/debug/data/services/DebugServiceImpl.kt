package com.markoid.packit.debug.data.services

import com.markoid.packit.debug.data.entities.LogDto
import com.markoid.packit.debug.data.entities.LogEntity
import com.markoid.packit.debug.domain.usecases.GetLogMessageUseCase
import com.markoid.packit.debug.domain.usecases.SaveLogMessageUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DebugServiceImpl(
    private val saveLogMessageUseCase: SaveLogMessageUseCase,
    private val getLogMessageUseCase: GetLogMessageUseCase
) : DebugService {

    private val logger = LoggerFactory.getLogger(DebugServiceImpl::class.java)

    companion object {
        const val SAVE_LOG_PARAM = "Log has been saved: {}"
        const val GET_LOG_PARAM = "Log request with user id: {}. Logs returned: {}"
    }

    override fun saveLogMessage(log: LogDto) {
        this.saveLogMessageUseCase.startCommand(log)
        this.logger.info(SAVE_LOG_PARAM, log)
    }

    override fun getLogMessagesByUserId(userId: String?): List<LogEntity> {
        val result = this.getLogMessageUseCase.startCommand(userId)
        this.logger.info(GET_LOG_PARAM, userId, result.size)
        return result
    }

}