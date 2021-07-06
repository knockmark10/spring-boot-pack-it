package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.MessageDictionary.*
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.BroadcastLocationDto

class BroadcastLocationUseCase(
    private val trackingRepository: TrackingRepository
) : AbstractUseCase<ApiResult, BroadcastLocationDto>() {

    override fun onExecuteTask(params: BroadcastLocationDto): ApiResult {
        // Request has been validated at this point, so null asserted is safe
        val history = HistoryEntity(
            address = params.address,
            city = params.city,
            date = params.date,
            latitude = params.latitude,
            longitude = params.longitude,
            shipmentStatus = params.shipmentStatus,
            state = params.state,
            userId = params.userId
        )
        // Get the trip requested. Throw an error if not found
        val trip = this.trackingRepository.getTripById(params.tripId)
            ?: throw raiseException(TRIP_NOT_FOUND)

        // Get the trip's attachments, EXCEPT ours.
        val attachments = trip.attachments
            .filter { t -> t.shipmentId != params.shipId }
            .toMutableList()

        // Get the attachment for this shipment id. Throw an error is none is found
        val attachment = trip.attachments
            .firstOrNull { t -> t.shipmentId == params.shipId }
            ?: throw raiseException(TRIP_ATTACHMENTS_NOT_FOUND)

        // Update the history list with the incoming one
        attachment.historyList.add(history)
        // Add the updated attachment to the list
        attachments.add(attachment)
        // Set it back to the trip
        trip.attachments = attachments
        // Perform the update operation on the database
        this.trackingRepository.saveTrip(trip)

        // Return success
        return buildSuccessfulMessage(TRIP_UPDATED_SUCCESSFULLY)
    }

}