package com.markoid.packit.shipments.presentation.controllers

import com.markoid.packit.core.presentation.controllers.BaseAuthController
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.domain.usecases.GetShipmentsUseCase
import com.markoid.packit.shipments.domain.usecases.SaveShipmentUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * This is the controller responsible for all the shipments
 */
@RequestMapping(ApiConstants.SHIPMENT_PATH)
@RestController
class ShipmentController(
    private val getShipmentsUseCase: GetShipmentsUseCase,
    private val saveShipmentUseCase: SaveShipmentUseCase
) : BaseAuthController() {

    @GetMapping
    fun getShipmentsByUserId(
        @RequestHeader(ApiConstants.USER_ID_PARAM, required = false) userId: String?
    ): ResponseEntity<List<ShipmentEntity>> = this.getShipmentsUseCase.execute(userId)


    @PostMapping
    fun saveShipment(
        @RequestBody request: ShipmentEntity
    ): ResponseEntity<ShipmentEntity> = this.saveShipmentUseCase.execute(request)

}