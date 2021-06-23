package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.tracking.data.repository.TrackingRepository
import org.springframework.http.ResponseEntity

class GetAttachedShipmentUseCase(
    private val authRepository: AuthRepository,
    private val trackingRepository: TrackingRepository
) : BaseUseCase<Map<String, String>, String>() {

    override fun onValidateRequest(userId: String): ValidationStatus =
        if (userId.isEmpty()) ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else ValidationStatus.Success

    override fun postValidatedExecution(userId: String): ResponseEntity<Map<String, String>> {
        // Search user by userId
        val user = this.authRepository.getUserById(userId) ?: throw raiseException(ExceptionDictionary.USER_NOT_FOUND)

        // Search the user's trip
        val trip = this.trackingRepository.getTripById(user.assignedTrip)
            ?: throw raiseException(ExceptionDictionary.TRIP_NOT_ATTACHED_TO_USER)

        // Get the shipment id from the trip's attachments
        val shipId = trip.attachments
            .firstOrNull { it.userId == user.id.toHexString() }?.shipmentId
            ?: throw raiseException(ExceptionDictionary.TRIP_SHIPMENT_NOT_ATTACHED_TO_USER)

        val shipMap = LinkedHashMap<String, String>()
        shipMap["shipId"] = shipId

        return buildResultMessage(shipMap)
    }

}