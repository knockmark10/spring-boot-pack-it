package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.domain.usecases.BaseUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.BaseUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.MISSING_PARAMETERS
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.presentation.utils.ShipmentValidator

class SaveShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) : BaseUseCase<ShipmentEntity, ShipmentEntity>() {

    override fun onValidateRequest(request: ShipmentEntity): ValidationStatus = when {
        request.shipId.isEmpty() ||
                ShipmentValidator.isFullNameValid(request.owner).not() ||
                ShipmentValidator.isShipmentNameValid(request.name).not() -> Failure(MISSING_PARAMETERS)
        else -> Success
    }

    override fun postValidatedExecution(request: ShipmentEntity): ShipmentEntity =
        this.shipmentRepository.saveNewShipment(request.userId, request)

}