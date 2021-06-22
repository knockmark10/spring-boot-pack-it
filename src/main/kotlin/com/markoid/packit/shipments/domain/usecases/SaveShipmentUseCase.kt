package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import org.springframework.http.ResponseEntity

class SaveShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
) : BaseUseCase<ShipmentEntity, ShipmentEntity>() {

    override fun postValidatedExecution(request: ShipmentEntity): ResponseEntity<ShipmentEntity> {
        val result = this.shipmentRepository.saveNewShipment(request.userId, request)
        return ResponseEntity.ok(result)
    }

}