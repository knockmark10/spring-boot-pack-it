package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.MessageDictionary
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.tracking.data.entities.TripStatus
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.results.UpdateTripDto

class UpdateTripStatusUseCase(
    private val authRepository: AuthRepository,
    private val shipmentRepository: ShipmentRepository,
    private val trackingRepository: TrackingRepository,
) : AbstractUseCase<ApiResult, UpdateTripDto>() {

    override fun onExecuteTask(params: UpdateTripDto): ApiResult {
        // Get trip by id
        val trip = this.trackingRepository.getTripById(params.tripId)
            ?: throw raiseException(MessageDictionary.TRIP_NOT_FOUND)

        // Update trip with incoming status
        this.trackingRepository.saveTrip(trip.copy(status = params.status))

        // Update users and shipments if the trip status is set to 'Archived'
        if (params.status == TripStatus.Archived) {
            // End time parameter should not be null when status is 'Archived'
            if (params.endTime == null) throw raiseException(MessageDictionary.MISSING_PARAMETERS)
            // Loop through attachments
            for (it in trip.attachments) {
                // Remove assigned trips to the user
                val user = this.authRepository.getUserById(it.userId) ?: continue
                this.authRepository.saveUser(user.copy(assignedTrip = ""))
                // Add delivery date to every archived shipment, with the provided end time
                val shipment = this.shipmentRepository.getShipmentByShipId(it.shipmentId)
                    ?: throw raiseException(MessageDictionary.SHIPMENT_NOT_FOUND)
                this.shipmentRepository.updateExistingShipment(it.userId, shipment.copy(receivedAt = params.endTime))
            }
        }

        return okMessage(MessageDictionary.TRIP_UPDATED_SUCCESSFULLY)
    }

}
