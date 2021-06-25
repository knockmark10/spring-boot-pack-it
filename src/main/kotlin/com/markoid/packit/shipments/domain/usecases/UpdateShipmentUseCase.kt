package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository

class UpdateShipmentUseCase(
    private val repository: ShipmentRepository
) : BaseUseCase<BaseResponse, ShipmentEntity>() {

    override fun onValidateRequest(request: ShipmentEntity): ValidationStatus =
        if (request.userId.isEmpty()) ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else ValidationStatus.Success

    override fun postValidatedExecution(request: ShipmentEntity): BaseResponse {
        // Check if shipment was updated
        if (this.repository.updateExistingShipment(request.userId, request)) {
            return buildOkMessage(ExceptionDictionary.SHIPMENT_UPDATE_SUCCESSFUL)
        } else {
            throw raiseException(ExceptionDictionary.SHIPMENT_UPDATE_ERROR)
        }
    }

}