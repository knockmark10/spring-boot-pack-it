package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository

class GetShipmentsUseCase(
    private val shipmentRepository: ShipmentRepository
) : AbstractUseCase<List<ShipmentEntity>, String>() {

    override fun onExecuteTask(params: String): List<ShipmentEntity> =
        this.shipmentRepository.getShipmentsByUserId(params)

}