package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.shipments.data.entities.ShipmentStatus
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.BroadcastLocationRequest
import org.springframework.http.ResponseEntity

class BroadcastLocationUseCase(
    private val trackingRepository: TrackingRepository
) : BaseUseCase<BaseResponse, BroadcastLocationRequest>() {

    override fun onValidateRequest(request: BroadcastLocationRequest): ValidationStatus = when {
        request.shipId.isNullOrEmpty() ||
                request.userId.isNullOrEmpty() ||
                request.latitude == null ||
                request.latitude == 0.0 ||
                request.longitude == null ||
                request.longitude == 0.0 ||
                request.state.isNullOrEmpty() ||
                request.city.isNullOrEmpty() ||
                request.date.isNullOrEmpty() ||
                request.tripId.isNullOrEmpty() -> ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else -> ValidationStatus.Success
    }

    override fun postValidatedExecution(request: BroadcastLocationRequest): ResponseEntity<BaseResponse> {
        // Request has been validated at this point, so null asserted is safe
        val history = HistoryEntity(
            address = request.address ?: "",
            city = request.city!!,
            date = request.date!!,
            latitude = request.latitude!!,
            longitude = request.longitude!!,
            shipmentStatus = request.shipmentStatus ?: ShipmentStatus.Idle,
            state = request.state!!,
            userId = request.userId!!
        )
        // Get the trip requested. Throw an error if not found
        val trip =
            this.trackingRepository.getTripById(request.tripId!!)
                ?: throw raiseException(ExceptionDictionary.TRIP_NOT_FOUND)

        // Get the trip's attachments, EXCEPT ours.
        val attachments = trip.attachments
            .filter { t -> t.shipmentId != request.shipId }
            .toMutableList()

        // Get the attachment for this shipment id. Throw an error is none is found
        val attachment = trip.attachments
            .firstOrNull { t -> t.shipmentId == request.shipId }
            ?: throw raiseException(ExceptionDictionary.TRIP_ATTACHMENTS_NOT_FOUND)

        // Update the history list with the incoming one
        attachment.historyList.add(history)
        // Add the updated attachment to the list
        attachments.add(attachment)
        // Set it back to the trip
        trip.attachments = attachments
        // Perform the update operation on the database
        this.trackingRepository.saveTrip(trip)

        // Return success
        return buildOkMessage(ExceptionDictionary.TRIP_UPDATED_SUCCESSFULLY)
    }

}