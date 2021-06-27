package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.*
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.domain.usecases.requests.DeleteShipmentDto

class DeleteShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) : AbstractUseCase<ApiResult, DeleteShipmentDto>() {

    override fun onHandleValidations(params: DeleteShipmentDto): ValidationStatus = when {
        params.shipId.isNullOrEmpty() || params.userId.isNullOrEmpty() -> ValidationStatus.Failure(MISSING_PARAMETERS)
        else -> ValidationStatus.Success
    }

    override fun onExecuteTask(params: DeleteShipmentDto): ApiResult =
        if (this.shipmentRepository.deleteShipmentByUserId(params.shipId!!, params.userId!!)) {
            // It it was deleted successfully, return success
            buildSuccessfulMessage(SHIPMENT_DELETION_SUCCESSFUL)
        } else {
            // Otherwise the deletion was not successful
            throw raiseException(SHIPMENT_DELETION_ERROR)
        }

}