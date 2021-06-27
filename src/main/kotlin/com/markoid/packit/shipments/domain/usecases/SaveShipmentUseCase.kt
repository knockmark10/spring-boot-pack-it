package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Failure
import com.markoid.packit.core.domain.usecases.AbstractUseCase.ValidationStatus.Success
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.MISSING_PARAMETERS
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.domain.usecases.requests.ShipmentEntityDto
import com.markoid.packit.shipments.domain.usecases.requests.mapToEntity

class SaveShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) : AbstractUseCase<ShipmentEntity, ShipmentEntityDto>() {

    /**
     * This is needed here since bean validation will not work. The rest of parameters have already been validated.
     */
    override fun onHandleValidations(params: ShipmentEntityDto): ValidationStatus =
        if (params.userId.isEmpty()) Failure(MISSING_PARAMETERS)
        else Success

    override fun onExecuteTask(params: ShipmentEntityDto): ShipmentEntity {
        val shipmentEntity = params.mapToEntity()
        this.shipmentRepository.saveNewShipment(params.userId, shipmentEntity)
        return shipmentEntity
    }

}