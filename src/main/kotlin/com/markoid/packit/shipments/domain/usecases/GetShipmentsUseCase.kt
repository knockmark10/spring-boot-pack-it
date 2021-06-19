package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.exceptions.IncompleteRequestException
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import org.springframework.http.ResponseEntity

class GetShipmentsUseCase(
    private val shipmentRepository: ShipmentRepository,
) : BaseUseCase<List<ShipmentEntity>, String?>() {

    override fun execute(request: String?): ResponseEntity<List<ShipmentEntity>> {
        // If there is no user id from the header, throw an exception
        if (request.isNullOrEmpty()) throw IncompleteRequestException()
        // Get shipments with the user id provided
        val shipments = this.shipmentRepository.getShipmentsByUserId(request)
        return ResponseEntity.ok(shipments)
    }

}