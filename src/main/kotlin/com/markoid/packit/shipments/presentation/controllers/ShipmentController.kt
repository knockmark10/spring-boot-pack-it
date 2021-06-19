package com.markoid.packit.shipments.presentation.controllers

import com.markoid.packit.core.presentation.controllers.BaseAuthController
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.domain.usecases.GetShipmentsUseCase
import com.markoid.packit.shipments.domain.usecases.SaveShipmentUseCase
import com.markoid.packit.shipments.domain.usecases.requests.GetShipmentRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * This is the controller responsible for all the shipments
 */
@RequestMapping("/api/v2/shipments")
@RestController
class ShipmentController(
    private val getShipmentsUseCase: GetShipmentsUseCase,
    private val saveShipmentUseCase: SaveShipmentUseCase
) : BaseAuthController() {

    @GetMapping
    fun getAllShipments(
        @RequestHeader("userid", required = false) userId: String?
    ): ResponseEntity<List<ShipmentEntity>> {
        return this.getShipmentsUseCase.execute(GetShipmentRequest(userId))
    }

    @PostMapping
    fun saveShipment(@RequestBody request: ShipmentEntity): ResponseEntity<ShipmentEntity> {
        return this.saveShipmentUseCase.execute(request)
    }

    /*@PostMapping
    fun saveShipment(): ResponseEntity<ShipmentEntity> {
        return this.saveShipmentUseCase.execute(emptyShipment())
    }*/

}