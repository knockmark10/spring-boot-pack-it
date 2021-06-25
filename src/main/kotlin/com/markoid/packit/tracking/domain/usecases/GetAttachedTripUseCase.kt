package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.*
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.results.GetAttachedTripResult

class GetAttachedTripUseCase(
    private val authRepository: AuthRepository,
    private val trackingRepository: TrackingRepository
) : BaseUseCase<GetAttachedTripResult, String>() {

    override fun onValidateRequest(request: String): ValidationStatus =
        if (request.isEmpty()) ValidationStatus.Failure(MISSING_PARAMETERS)
        else ValidationStatus.Success

    override fun postValidatedExecution(userId: String): GetAttachedTripResult {
        // Get the user
        val user = this.authRepository.getUserById(userId) ?: throw raiseException(USER_NOT_FOUND)

        // Make sure there is an assigned trip to him
        if (user.assignedTrip.isEmpty()) throw raiseException(TRIP_DRIVERS_NOT_FOUND)

        // Get the trip or return an error
        val trip = this.trackingRepository.getTripById(user.assignedTrip) ?: throw raiseException(TRIP_NOT_FOUND)

        val shipId = trip.attachments.firstOrNull { it.userId == userId }?.shipmentId ?: ""

        return GetAttachedTripResult(trip.id.toHexString(), shipId)
    }

}