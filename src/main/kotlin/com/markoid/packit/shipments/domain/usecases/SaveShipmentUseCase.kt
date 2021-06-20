package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity

class SaveShipmentUseCase(
    private val shipmentRepository: ShipmentRepository,
    messageSource: MessageSource
) : BaseUseCase<ShipmentEntity, ShipmentEntity>(messageSource) {

    override fun execute(request: ShipmentEntity): ResponseEntity<ShipmentEntity> {
        val result = this.shipmentRepository.saveShipment(request.userId, request)
        return ResponseEntity.ok(result)
    }

}