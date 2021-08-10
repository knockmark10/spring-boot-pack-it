package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.MessageDictionary
import com.markoid.packit.core.presentation.handlers.MessageDictionary.SHIPMENT_DELETION_ERROR
import com.markoid.packit.core.presentation.handlers.MessageDictionary.SHIPMENT_NOT_FOUND
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.entities.ShipmentStatus
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.domain.usecases.requests.DeleteShipmentDto
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.data.entities.TripStatus
import com.markoid.packit.tracking.data.repository.TrackingRepository

class DeleteShipmentUseCase(
    private val authRepository: AuthRepository,
    private val shipmentRepository: ShipmentRepository,
    private val trackingRepository: TrackingRepository
) : AbstractUseCase<ApiResult, DeleteShipmentDto>() {

    /**
     * This will execute the shipment deletion. [TripEntity] and [ShipmentEntity] needs to be checked and validated
     * before proceeding to delete the shipment.
     */
    override fun onExecuteTask(params: DeleteShipmentDto): ApiResult {
        // Get a trip by user id.
        getTrip(params.userId)?.let {
            // There is a trip found. Make sure its status is set to 'Inactive'
            if (it.status == TripStatus.Inactive) {
                forwardToDeleteShipment(params, it)
            } else {
                // Trip status is not 'Inactive'. Therefore, the shipment cannot be deleted.
                throw raiseException(SHIPMENT_DELETION_ERROR)
            }
        } ?: run {
            // There is no trip to check. Just delete the shipment
            forwardToDeleteShipment(params)
        }

        // If everything went good, return successful message.
        return okMessage(MessageDictionary.SHIPMENT_DELETION_SUCCESSFUL)
    }

    /**
     * This method will delete the shipment from the database. In order to be deleted, the shipment status must
     * be set to [ShipmentStatus.Idle]. Otherwise, the shipment will not be deleted
     */
    private fun forwardToDeleteShipment(params: DeleteShipmentDto, trip: TripEntity? = null) {
        val shipment = getShipment(params.shipId)

        // Shipment must be set to 'Idle' before proceeding to delete it.
        if (shipment.status == ShipmentStatus.Idle) {
            // Delete shipment from database. If 'false' is returned, thrown an error.
            val wasDeletedFromDatabase = this.shipmentRepository.deleteShipmentByUserId(params.shipId, params.userId)
            if (wasDeletedFromDatabase.not()) throw raiseException(SHIPMENT_DELETION_ERROR)

            // Remove shipment from trip (only if there is one)
            trip?.let { removeShipmentFromTrip(params, it) }
        } else {
            // Shipment is not Idle. Therefore it cannot be deleted.
            throw raiseException(SHIPMENT_DELETION_ERROR)
        }
    }

    /**
     * Gets a trip with the [userId] provided.
     */
    private fun getTrip(userId: String): TripEntity? {
        val tripId = this.authRepository.getUserById(userId)?.assignedTrip ?: return null
        return this.trackingRepository.getTripById(tripId)
    }

    /**
     * Gets a shipment with the [shipId] provided.
     * It will thrown an error if no [ShipmentEntity] is found.
     */
    private fun getShipment(shipId: String): ShipmentEntity =
        this.shipmentRepository.getShipmentByShipId(shipId) ?: throw raiseException(SHIPMENT_NOT_FOUND)

    /**
     * Removes a shipment attached to a trip. By removing it two things could happen:
     *
     * 1) There are more shipments attached to the trip: Only remove the one requested.
     * 2) There is only one shipment on the trip: Clear [TripEntity.attachments].
     */
    private fun removeShipmentFromTrip(request: DeleteShipmentDto, trip: TripEntity) {
        val isShipmentAttachedToTrip = trip.attachments.any { it.userId == request.userId }
        if (isShipmentAttachedToTrip) {
            if (trip.attachments.size > 1) {
                // This is the first case mentioned. Just filter out the shipment from this user
                trip.attachments = trip.attachments.filter { it.userId != it.userId }.toMutableList()
            } else {
                // This is the second case mentioned. Clear the attachments list.
                trip.attachments.clear()
            }
            // Finally update the trip.
            this.trackingRepository.saveTrip(trip)
        } else {
            // There is an inconsistency with the data stored. Throw an exception
            throw RuntimeException("Data inconsistency while deleting the shipment.")
        }
    }

}