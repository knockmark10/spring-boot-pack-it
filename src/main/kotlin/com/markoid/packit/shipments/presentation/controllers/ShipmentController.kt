package com.markoid.packit.shipments.presentation.controllers

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
) {

    @GetMapping
    fun getShipmentsByUserId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String?
    ): ResponseEntity<List<ShipmentEntity>> = this.getShipmentsUseCase.setLanguage(language).execute(userId)

    @PostMapping
    fun saveShipment(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody request: ShipmentEntity
    ): ResponseEntity<ShipmentEntity> = this.saveShipmentUseCase.setLanguage(language).execute(request)

}