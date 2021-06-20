package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.CreateNewTripRequest
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity

class CreateNewTripUseCase/*(
    private val authRepository: AuthRepository,
    private val trackingRepository: TrackingRepository,
    messageSource: MessageSource
) : BaseUseCase<TripEntity, CreateNewTripRequest?>(messageSource) {

    override fun execute(request: CreateNewTripRequest?): ResponseEntity<TripEntity> {
        validateRequest(request) {

        }
    }

    private fun validateRequest(request: CreateNewTripRequest?, block: (CreateNewTripRequest) -> Unit) = when {
        request == null ||
                request.driverId.isNullOrEmpty() ||
                request.userId.isNullOrEmpty() ||
                request.tripStatus == null -> throw raiseException(ExceptionDictionary.MISSING_PARAMETERS)
        doesDriverExist(request.driverId).not() -> throw raiseException(ExceptionDictionary.DRIVER_NOT_FOUND)
        else -> block.invoke(request)
    }

    private fun doesDriverExist(driverId: String): Boolean =
        this.authRepository.getDriverByUserId(driverId) != null

}*/