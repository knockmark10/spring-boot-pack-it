package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.tracking.data.entities.AttachmentsEntity
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.AttachTrackerRequest

class AttachTrackerUseCase(
    private val authRepository: AuthRepository,
    private val trackingRepository: TrackingRepository
) : BaseUseCase<BaseResponse, AttachTrackerRequest>() {

    /**
     * Validates the [AttachTrackerRequest] request.
     */
    override fun onValidateRequest(request: AttachTrackerRequest): ValidationStatus = when {
        request.driverId.isNullOrEmpty() ||
                request.shipId.isNullOrEmpty() ||
                request.tripId.isNullOrEmpty() ||
                request.userId.isNullOrEmpty() -> ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else -> ValidationStatus.Success
    }

    override fun postValidatedExecution(request: AttachTrackerRequest): BaseResponse {
        // Check if user exists. Throw error otherwise
        val user =
            this.authRepository.getUserById(request.userId!!)
                ?: throw raiseException(ExceptionDictionary.USER_NOT_FOUND)
        // Check if trip exists. Throw error otherwise
        val trip =
            this.trackingRepository.getTripById(request.tripId!!)
                ?: throw raiseException(ExceptionDictionary.TRIP_NOT_FOUND)
        // Make sure the trip belongs to the driver
        if (trip.driverId == request.driverId) {
            appendUserShipmentsToTheTrip(request, trip, user)
        } else {
            // Show error
            throw raiseException(ExceptionDictionary.TRIP_UNATACHABLE)
        }
        // If everything went good, return successful message
        return buildOkMessage(ExceptionDictionary.TRIP_ATTACHED_SUCCESSFULLY)
    }

    /**
     * This will append the user's [ShipmentEntity] to the trip, and update the 'assignedTrip' property to the [UserEntity].
     */
    private fun appendUserShipmentsToTheTrip(request: AttachTrackerRequest, trip: TripEntity, userEntity: UserEntity) {
        // Create user's attachments
        val userAttachments = AttachmentsEntity(shipmentId = request.shipId!!, userId = request.userId!!)
        // Check if user's shipment has not been attached previously
        val shipmentAlreadyAttached = trip.attachments.any { it.shipmentId == userAttachments.shipmentId }
        if (shipmentAlreadyAttached) {
            // Show error message
            throw raiseException(ExceptionDictionary.TRIP_SHIPMENT_ATTACHED_PREVIOUSLY)
        } else {
            // Shipment is not attached to trip yet, so we can attach it
            trip.attachments.add(userAttachments)
            // Save it in the system
            this.trackingRepository.saveTrip(trip)
            // Update 'assignedTrip' property on the user to let know that there is a trip assigned to it
            userEntity.assignedTrip = trip.id
            // Perform database update
            this.authRepository.saveUser(userEntity)
        }
    }

}