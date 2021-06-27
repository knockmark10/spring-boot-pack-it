package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.domain.usecases.requests.ShipmentEntityDto
import com.markoid.packit.shipments.domain.usecases.requests.mapToEntity

class SaveShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) : AbstractUseCase<ShipmentEntity, ShipmentEntityDto>() {

    override fun onExecuteTask(params: ShipmentEntityDto): ShipmentEntity {
        val shipmentEntity = params.mapToEntity()
        this.shipmentRepository.saveNewShipment(params.userId, shipmentEntity)
        return shipmentEntity
    }

}