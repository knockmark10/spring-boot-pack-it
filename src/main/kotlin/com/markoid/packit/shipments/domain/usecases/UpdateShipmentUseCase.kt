package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository

class UpdateShipmentUseCase(
    private val repository: ShipmentRepository
) : AbstractUseCase<ApiResult, ShipmentEntity>() {

    override fun onExecuteTask(params: ShipmentEntity): ApiResult {
        // Check if shipment was updated
        if (this.repository.updateExistingShipment(params.userId, params)) {
            return buildSuccessfulMessage(ExceptionDictionary.SHIPMENT_UPDATE_SUCCESSFUL)
        } else {
            throw raiseException(ExceptionDictionary.SHIPMENT_UPDATE_ERROR)
        }
    }

}