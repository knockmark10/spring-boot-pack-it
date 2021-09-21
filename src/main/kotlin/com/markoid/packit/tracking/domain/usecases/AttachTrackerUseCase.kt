package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.MessageDictionary
import com.markoid.packit.tracking.data.entities.AttachmentsEntity
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.AttachTrackerDto

class AttachTrackerUseCase(
    private val authRepository: AuthRepository,
    private val trackingRepository: TrackingRepository
) : AbstractUseCase<ApiResult, AttachTrackerDto>() {

    override fun onExecuteTask(params: AttachTrackerDto): ApiResult {
        // Check if user exists. Throw error otherwise
        val user =
            this.authRepository.getUserById(params.userId)
                ?: throw raiseException(MessageDictionary.USER_NOT_FOUND)
        // Check if trip exists. Throw error otherwise
        val trip =
            this.trackingRepository.getTripById(params.tripId)
                ?: throw raiseException(MessageDictionary.TRIP_NOT_FOUND)
        // Make sure the trip belongs to the driver
        if (trip.driverId == params.driverId) {
            appendUserShipmentsToTheTrip(params, trip, user)
        } else {
            // Show error
            throw raiseException(MessageDictionary.TRIP_UNATACHABLE)
        }
        // If everything went good, return successful message
        return okMessage(MessageDictionary.TRIP_ATTACHED_SUCCESSFULLY)
    }

    /**
     * This will append the user's [ShipmentEntity] to the trip, and update the 'assignedTrip' property to the [UserEntity].
     */
    private fun appendUserShipmentsToTheTrip(request: AttachTrackerDto, trip: TripEntity, userEntity: UserEntity) {
        // Create user's attachments
        val userAttachments = AttachmentsEntity(shipmentId = request.shipId, userId = request.userId)
        // Check if user's shipment has not been attached previously
        val shipmentAlreadyAttached = trip.attachments.any { it.shipmentId == userAttachments.shipmentId }
        if (shipmentAlreadyAttached) {
            // Show error message
            throw raiseException(MessageDictionary.TRIP_SHIPMENT_ATTACHED_PREVIOUSLY)
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