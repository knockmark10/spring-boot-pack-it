package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.authentication.domain.exceptions.AccessNotGrantedException
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.shipments.data.dao.ShipmentDao
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.domain.usecases.requests.GetShipmentRequest
import com.markoid.packit.shipments.presentation.utils.isNotNullOrEmpty
import org.springframework.http.ResponseEntity

class GetShipmentsUseCase(
    private val shipmentRepository: ShipmentRepository,
) : BaseUseCase<List<ShipmentEntity>, GetShipmentRequest>() {

    override fun execute(request: GetShipmentRequest): ResponseEntity<List<ShipmentEntity>> {
        return if (isValidRequest(request)) {
            val shipments = this.shipmentRepository.getAllShipments()
            print("Shipments is ${shipments.joinToString(", ")}")
            ResponseEntity.ok(shipments)
        } else {
            throw AccessNotGrantedException()
        }
    }

    private fun isValidRequest(request: GetShipmentRequest): Boolean =
        request.headerUserId.isNotNullOrEmpty()
//        true

}