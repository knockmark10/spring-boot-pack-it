package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.UpdateShipmentStatusRequest

class UpdateShipmentStatusUseCase(
    private val shipmentRepository: ShipmentRepository,
    private val trackingRepository: TrackingRepository
) : BaseUseCase<BaseResponse, UpdateShipmentStatusRequest>() {

    override fun onValidateRequest(request: UpdateShipmentStatusRequest): ValidationStatus = when {
        request.tripId.isNullOrEmpty() || request.shipId.isNullOrEmpty() || request.shipmentStatus == null ->
            ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else -> ValidationStatus.Success
    }

    override fun postValidatedExecution(request: UpdateShipmentStatusRequest): BaseResponse {
        // Look for the trip
        val trip = this.trackingRepository.getTripById(request.tripId!!)
            ?: throw raiseException(ExceptionDictionary.TRIP_NOT_FOUND)

        // Validate that the shipment provided is contained inside the attachments
        val attachment = trip.attachments.firstOrNull { it.shipmentId == request.shipId }
            ?: throw raiseException(ExceptionDictionary.TRIP_SHIPMENT_NOT_BELONG)

        // Get the shipment, or throw a shipment update error, since it won't be possible to perform the update
        val shipment = this.shipmentRepository.getShipmentByShipId(request.shipId!!)
            ?: throw raiseException(ExceptionDictionary.SHIPMENT_UPDATE_ERROR)

        //Finally update the shipment's status.
        this.shipmentRepository
            .updateExistingShipment(attachment.userId, shipment.copy(status = request.shipmentStatus!!))

        // Return success message
        return buildOkMessage(ExceptionDictionary.SHIPMENT_UPDATE_SUCCESSFUL)
    }

}