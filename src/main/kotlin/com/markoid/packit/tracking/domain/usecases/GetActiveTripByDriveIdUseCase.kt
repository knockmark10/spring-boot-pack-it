package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.tracking.data.entities.AttachmentsEntity
import com.markoid.packit.tracking.data.entities.TripStatus
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.results.ShipmentResult
import com.markoid.packit.tracking.domain.usecases.results.TripResult

class GetActiveTripByDriveIdUseCase(
    private val authRepository: AuthRepository,
    private val shipmentRepository: ShipmentRepository,
    private val trackingRepository: TrackingRepository
) : AbstractUseCase<TripResult, String>() {

    override fun onHandleValidations(params: String): ValidationStatus = when {
        params.isEmpty() -> ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else -> ValidationStatus.Success
    }

    override fun onExecuteTask(driverId: String): TripResult {
        // Get the trip with the driver associated. Return empty json instead
        val trip =
            trackingRepository.getTripByDriverId(driverId) ?: return TripResult()

        // The trip should be active or inactive
        if (trip.status == TripStatus.Archived) throw raiseException(ExceptionDictionary.TRIP_NOT_FOUND)

        // Build the shipment list from the trip attachments
        val shipments = buildShipmentList(trip.attachments)

        // Return proper object
        return TripResult(
            tripId = trip.id,
            status = trip.status,
            shipments = shipments
        )
    }

    private fun buildShipmentList(attachments: List<AttachmentsEntity>): List<ShipmentResult> {
        // Build the shipment list from the trip attachments
        val shipments = mutableListOf<ShipmentResult>()
        for (it in attachments) {
            // Get trip's shipment. If we don't find any, let's keep looping
            val shipment = this.shipmentRepository.getShipmentByShipId(it.shipmentId) ?: continue
            // Extract the firebase token from the user. It can be empty
            val firebaseToken = this.authRepository.getUserById(it.userId)?.firebaseToken ?: ""
            // Finally map the shipments and add them to the list
            shipments.add(shipment.mapToResult(firebaseToken))
        }
        // Return the shipments
        return shipments
    }

}

fun ShipmentEntity.mapToResult(firebaseToken: String): ShipmentResult = ShipmentResult(
    owner = this.owner,
    status = this.status,
    id = this.shipId,
    deliveryDirection = this.deliveryDirection,
    firebaseToken = firebaseToken
)