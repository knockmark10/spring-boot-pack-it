package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.MessageDictionary
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.UpdateShipmentStatusDto

class UpdateShipmentStatusUseCase(
    private val shipmentRepository: ShipmentRepository,
    private val trackingRepository: TrackingRepository
) : AbstractUseCase<ApiResult, UpdateShipmentStatusDto>() {

    override fun onExecuteTask(params: UpdateShipmentStatusDto): ApiResult {
        // Look for the trip
        val trip = this.trackingRepository.getTripById(params.tripId)
            ?: throw raiseException(MessageDictionary.TRIP_NOT_FOUND)

        // Validate that the shipment provided is contained inside the attachments
        val attachment = trip.attachments.firstOrNull { it.shipmentId == params.shipId }
            ?: throw raiseException(MessageDictionary.TRIP_SHIPMENT_NOT_BELONG)

        // Get the shipment, or throw a shipment update error, since it won't be possible to perform the update
        val shipment = this.shipmentRepository.getShipmentByShipId(params.shipId)
            ?: throw raiseException(MessageDictionary.SHIPMENT_UPDATE_ERROR)

        //Finally update the shipment's status.
        this.shipmentRepository
            .updateExistingShipment(attachment.userId, shipment.copy(status = params.shipmentStatus))

        // Return success message
        return okMessage(MessageDictionary.SHIPMENT_UPDATE_SUCCESSFUL)
    }

}