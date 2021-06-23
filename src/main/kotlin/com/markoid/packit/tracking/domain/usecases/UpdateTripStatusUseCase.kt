package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.tracking.data.entities.TripStatus
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.results.UpdateTripRequest

class UpdateTripStatusUseCase(
    private val authRepository: AuthRepository,
    private val shipmentRepository: ShipmentRepository,
    private val trackingRepository: TrackingRepository,
) : BaseUseCase<BaseResponse, UpdateTripRequest>() {

    override fun onValidateRequest(request: UpdateTripRequest): ValidationStatus = when {
        request.tripId.isNullOrEmpty() ||
                request.status == null ||
                // Archived status required end time to be valid
                (request.status == TripStatus.Archived && request.endTime.isNullOrEmpty()) ->
            ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else -> ValidationStatus.Success
    }

    override fun postValidatedExecution(request: UpdateTripRequest): BaseResponse {
        // Get trip by id
        val trip = this.trackingRepository.getTripById(request.tripId!!)
            ?: throw raiseException(ExceptionDictionary.TRIP_NOT_FOUND)

        // Update trip with incoming status
        this.trackingRepository.saveTrip(trip.copy(status = request.status))

        // Update users and shipments if the trip status is set to 'Archived'
        if (request.status == TripStatus.Archived) {
            for (it in trip.attachments) {
                // Remove assigned trips to the user
                val user = this.authRepository.getUserById(it.userId) ?: continue
                this.authRepository.saveUser(user.copy(assignedTrip = ""))
                // Add delivery date to every archived shipment, with the provided end time
                val shipment = this.shipmentRepository.getShipmentByShipId(it.shipmentId)
                    ?: throw raiseException(ExceptionDictionary.SHIPMENT_NOT_FOUND)
                this.shipmentRepository.updateExistingShipment(it.userId, shipment.copy(receivedAt = request.endTime))
            }
        }

        return buildOkMessage(ExceptionDictionary.TRIP_UPDATED_SUCCESSFULLY)
    }

}
