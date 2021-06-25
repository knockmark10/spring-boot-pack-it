package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.domain.usecases.BaseUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.BaseUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.*
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository

class GetHistoryByUserIdUseCase(
    private val authRepository: AuthRepository,
    private val trackingRepository: TrackingRepository
) : BaseUseCase<List<HistoryEntity>, String>() {

    override fun onValidateRequest(request: String): ValidationStatus =
        if (request.isEmpty()) Failure(MISSING_PARAMETERS)
        else Success

    override fun postValidatedExecution(userId: String): List<HistoryEntity> {
        val user = this.authRepository.getUserById(userId) ?: throw raiseException(USER_NOT_FOUND)
        if (user.assignedTrip.isEmpty()) throw raiseException(TRIP_USER_NOT_FOUND)
        val trip = this.trackingRepository.getTripById(user.assignedTrip) ?: throw raiseException(TRIP_NOT_FOUND)
        return trip.attachments.firstOrNull { it.userId == userId }?.historyList ?: emptyList()
    }

}