package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository

class SaveShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) : BaseUseCase<ShipmentEntity, ShipmentEntity>() {

    override fun postValidatedExecution(request: ShipmentEntity): ShipmentEntity =
        this.shipmentRepository.saveNewShipment(request.userId, request)

}