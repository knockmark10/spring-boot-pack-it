package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.CreateNewTripRequest
import org.springframework.http.ResponseEntity

class CreateNewTripUseCase(
    private val authRepository: AuthRepository,
    private val trackingRepository: TrackingRepository
) : BaseUseCase<TripEntity, CreateNewTripRequest>() {

    override fun onValidateRequest(request: CreateNewTripRequest): ValidationStatus = when {
        request.driverId.isNullOrEmpty() ||
                request.userId.isNullOrEmpty() ||
                request.tripStatus == null -> throw raiseException(ExceptionDictionary.MISSING_PARAMETERS)
        doesDriverExist(request.driverId).not() -> throw raiseException(ExceptionDictionary.DRIVER_NOT_FOUND)
        else -> ValidationStatus.Success
    }

    override fun postValidatedExecution(request: CreateNewTripRequest): ResponseEntity<TripEntity> {
        // At this point, the request and its parameters are fully validated
        val tripEntity = TripEntity(driverId = request.driverId, status = request.tripStatus)
        // Save trip in system
        this.trackingRepository.saveTrip(tripEntity)
        // Create response entity and return it
        return buildResultMessage(tripEntity)
    }

    /**
     * Check in database if driver exists
     */
    private fun doesDriverExist(driverId: String): Boolean =
        this.authRepository.getDriverByUserId(driverId) != null

}