package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.domain.usecases.BaseUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.BaseUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.*
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.domain.usecases.requests.DeleteShipmentRequest

class DeleteShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) : BaseUseCase<BaseResponse, DeleteShipmentRequest>() {

    override fun onValidateRequest(request: DeleteShipmentRequest): ValidationStatus = when {
        request.shipId.isNullOrEmpty() || request.userId.isNullOrEmpty() -> Failure(MISSING_PARAMETERS)
        else -> Success
    }

    override fun postValidatedExecution(request: DeleteShipmentRequest): BaseResponse =
        if (this.shipmentRepository.deleteShipmentByUserId(request.shipId!!, request.userId!!)) {
            // It it was deleted successfully, return success
            buildOkMessage(SHIPMENT_DELETION_SUCCESSFUL)
        } else {
            // Otherwise the deletion was not successful
            throw raiseException(SHIPMENT_DELETION_ERROR)
        }

}