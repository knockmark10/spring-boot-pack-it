package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.DRIVER_NOT_FOUND
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.CreateNewTripDto

class CreateNewTripUseCase(
    private val authRepository: AuthRepository,
    private val trackingRepository: TrackingRepository
) : AbstractUseCase<TripEntity, CreateNewTripDto>() {

    override fun onHandleValidations(params: CreateNewTripDto): ValidationStatus =
        if (doesDriverExist(params.driverId).not()) Failure(DRIVER_NOT_FOUND)
        else Success

    override fun onExecuteTask(params: CreateNewTripDto): TripEntity {
        // At this point, the request and its parameters are fully validated
        val tripEntity = TripEntity(driverId = params.driverId, status = params.tripStatus)
        // Save trip in system
        this.trackingRepository.saveTrip(tripEntity)
        // Create response entity and return it
        return tripEntity
    }

    /**
     * Check in database if driver exists
     */
    private fun doesDriverExist(driverId: String): Boolean =
        this.authRepository.getDriverByUserId(driverId) != null

}