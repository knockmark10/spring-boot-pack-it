package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.MessageDictionary.SHIPMENT_UPDATE_ERROR
import com.markoid.packit.core.presentation.handlers.MessageDictionary.SHIPMENT_UPDATE_SUCCESSFUL
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.domain.usecases.requests.ShipmentEntityDto
import com.markoid.packit.shipments.domain.usecases.requests.mapToEntity

class UpdateShipmentUseCase(
    private val repository: ShipmentRepository
) : AbstractUseCase<ApiResult, ShipmentEntityDto>() {

    override fun onExecuteTask(params: ShipmentEntityDto): ApiResult {
        val shipmentEntity = params.mapToEntity()
        // Check if shipment was updated
        if (this.repository.updateExistingShipment(params.userId, shipmentEntity)) {
            return okMessage(SHIPMENT_UPDATE_SUCCESSFUL)
        } else {
            throw raiseException(SHIPMENT_UPDATE_ERROR)
        }
    }

}