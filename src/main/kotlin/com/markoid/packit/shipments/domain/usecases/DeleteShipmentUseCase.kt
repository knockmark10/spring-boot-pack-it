package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import org.springframework.http.ResponseEntity

class DeleteShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) : BaseUseCase<BaseResponse, ShipmentEntity>() {

    override fun postValidatedExecution(request: ShipmentEntity): ResponseEntity<BaseResponse> {
        val wasDeleted = this.shipmentRepository.deleteShipmentById(request.shipId)
        if (wasDeleted) {
            return buildOkMessage(ExceptionDictionary.SHIPMENT_DELETION_SUCCESSFUL)
        } else {
            throw raiseException(ExceptionDictionary.SHIPMENT_DELETION_ERROR)
        }
    }

}