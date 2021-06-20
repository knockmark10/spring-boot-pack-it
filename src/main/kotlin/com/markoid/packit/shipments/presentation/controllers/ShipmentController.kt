package com.markoid.packit.shipments.presentation.controllers

import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.domain.usecases.DeleteShipmentUseCase
import com.markoid.packit.shipments.domain.usecases.GetShipmentsUseCase
import com.markoid.packit.shipments.domain.usecases.SaveShipmentUseCase
import com.markoid.packit.shipments.domain.usecases.UpdateShipmentUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * This is the controller responsible for all the shipments
 */
@RequestMapping(ApiConstants.SHIPMENT_PATH)
@RestController
class ShipmentController(
    private val deleteShipmentUseCase: DeleteShipmentUseCase,
    private val getShipmentsUseCase: GetShipmentsUseCase,
    private val saveShipmentUseCase: SaveShipmentUseCase,
    private val updateShipmentUseCase: UpdateShipmentUseCase
) {

    @DeleteMapping
    fun deleteShipmentByUserId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String,
        @RequestBody request: ShipmentEntity
    ): ResponseEntity<BaseResponse> =
        this.deleteShipmentUseCase.setLanguage(language).execute(request.copy(userId = userId))

    @GetMapping
    fun getShipmentsByUserId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String?
    ): ResponseEntity<List<ShipmentEntity>> = this.getShipmentsUseCase.setLanguage(language).execute(userId)

    @PostMapping
    fun saveNewShipment(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String,
        @RequestBody request: ShipmentEntity
    ): ResponseEntity<ShipmentEntity> =
        this.saveShipmentUseCase.setLanguage(language).execute(request.copy(userId = userId))

    @PutMapping
    fun updateExistingShipment(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String,
        @RequestBody request: ShipmentEntity
    ): ResponseEntity<BaseResponse> =
        this.updateShipmentUseCase.setLanguage(language).execute(request.copy(userId = userId))

}