package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.*
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.domain.usecases.requests.ShipmentEntityDto
import com.markoid.packit.shipments.domain.usecases.requests.mapToEntity

class UpdateShipmentUseCase(
    private val repository: ShipmentRepository
) : AbstractUseCase<ApiResult, ShipmentEntityDto>() {

    override fun onHandleValidations(params: ShipmentEntityDto): ValidationStatus =
        if (params.userId.isEmpty()) Failure(MISSING_PARAMETERS)
        else Success

    override fun onExecuteTask(params: ShipmentEntityDto): ApiResult {
        val shipmentEntity = params.mapToEntity()
        // Check if shipment was updated
        if (this.repository.updateExistingShipment(params.userId, shipmentEntity)) {
            return buildSuccessfulMessage(SHIPMENT_UPDATE_SUCCESSFUL)
        } else {
            throw raiseException(SHIPMENT_UPDATE_ERROR)
        }
    }

}