package com.markoid.packit.shipments.presentation.controllers

import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.domain.usecases.*
import com.markoid.packit.shipments.domain.usecases.requests.DeleteShipmentRequest
import com.markoid.packit.shipments.domain.usecases.results.GenerateIdResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * This is the controller responsible for all the shipments
 */
@RequestMapping(ApiConstants.SHIPMENT_PATH)
@RestController
class ShipmentController(
    private val deleteShipmentUseCase: DeleteShipmentUseCase,
    private val generateShipmentIdUseCase: GenerateShipmentIdUseCase,
    private val getShipmentsUseCase: GetShipmentsUseCase,
    private val saveShipmentUseCase: SaveShipmentUseCase,
    private val updateShipmentUseCase: UpdateShipmentUseCase
) {

    @DeleteMapping
    fun deleteShipmentByUserId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_SHIPMENT_ID, required = false) shipId: String?,
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String?,
    ): ResponseEntity<BaseResponse> = this.deleteShipmentUseCase.setLanguage(language)
        .startCommand(DeleteShipmentRequest(shipId, userId))

    @GetMapping(ApiConstants.GENERATE_SHIPMENT_ID)
    fun generateShipmentId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en"
    ): ResponseEntity<GenerateIdResult> = this.generateShipmentIdUseCase.setLanguage(language).startCommand(Unit)

    @GetMapping
    fun getShipmentsByUserId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String?
    ): ResponseEntity<List<ShipmentEntity>> = this.getShipmentsUseCase.setLanguage(language).startCommand(userId)

    @PostMapping
    fun saveNewShipment(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String,
        @RequestBody request: ShipmentEntity
    ): ResponseEntity<ShipmentEntity> = this.saveShipmentUseCase.setLanguage(language)
        .startCommand(request.copy(userId = userId))

    @PutMapping
    fun updateExistingShipment(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String?,
        @RequestBody request: ShipmentEntity?
    ): ResponseEntity<BaseResponse> = this.updateShipmentUseCase.setLanguage(language)
        .startCommand(request?.copy(userId = userId ?: ""))

}