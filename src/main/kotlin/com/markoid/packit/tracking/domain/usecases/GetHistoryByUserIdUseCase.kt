package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.*
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository

class GetHistoryByUserIdUseCase(
    private val authRepository: AuthRepository,
    private val trackingRepository: TrackingRepository
) : AbstractUseCase<List<HistoryEntity>, String>() {

    override fun onHandleValidations(params: String): ValidationStatus =
        if (params.isEmpty()) Failure(MISSING_PARAMETERS)
        else Success

    override fun onExecuteTask(userId: String): List<HistoryEntity> {
        val user = this.authRepository.getUserById(userId) ?: throw raiseException(USER_NOT_FOUND)
        if (user.assignedTrip.isEmpty()) throw raiseException(TRIP_USER_NOT_FOUND)
        val trip = this.trackingRepository.getTripById(user.assignedTrip) ?: throw raiseException(TRIP_NOT_FOUND)
        return trip.attachments.firstOrNull { it.userId == userId }?.historyList ?: emptyList()
    }

}