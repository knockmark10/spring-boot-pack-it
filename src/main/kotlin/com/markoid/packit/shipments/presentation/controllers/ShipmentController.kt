package com.markoid.packit.shipments.presentation.controllers

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.domain.usecases.*
import com.markoid.packit.shipments.domain.usecases.requests.DeleteShipmentDto
import com.markoid.packit.shipments.domain.usecases.requests.ShipmentEntityDto
import com.markoid.packit.shipments.domain.usecases.results.GenerateIdResult
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * This is the controller responsible for all the operations supported for shipments.
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

    private val logger = LoggerFactory.getLogger(ShipmentController::class.java)

    companion object {
        const val DELETE_SHIPMENT_LOG = "Shipment with id: {} from user: {} was deleted."
        const val GENERATE_SHIPMENT_ID_LOG = "New shipment id generated: {}"
        const val GET_SHIPMENTS_BY_USER_ID_LOG = "User with id {} have requested a list of shipments: {}"
        const val SAVE_NEW_SHIPMENT_LOG = "User with id {} has saved shipment {}"
        const val UPDATE_SHIPMENT_LOG = "User with id {} has updated his shipment {}"
    }

    @ApiOperation("Deletes a shipment with the id provided.")
    @ApiResponse(
        code = 200,
        message = "A message indicating that the deletion was performed successfully.",
        response = ApiResult::class
    )
    @DeleteMapping
    fun deleteShipmentByUserId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody @Valid body: DeleteShipmentDto?
    ): ResponseEntity<ApiResult> {
        val result = this.deleteShipmentUseCase.setLanguage(language).startCommand(body)
        this.logger.info(DELETE_SHIPMENT_LOG, body?.shipId, body?.userId)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("Generates a new shipment id to be used by client app.")
    @ApiResponse(
        code = 200,
        message = "The string ID generated",
        response = GenerateIdResult::class
    )
    @GetMapping(ApiConstants.GENERATE_SHIPMENT_ID)
    fun generateShipmentId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en"
    ): ResponseEntity<GenerateIdResult> {
        val generatedId = this.generateShipmentIdUseCase.setLanguage(language).startCommand(Unit)
        this.logger.info(GENERATE_SHIPMENT_ID_LOG, generatedId)
        return ResponseEntity.ok(generatedId)
    }

    @ApiOperation("Retrieves a list of shipments that belong to a user.")
    @ApiResponse(
        code = 200,
        message = "The list of shipments owned by the user.",
        response = Array<ShipmentEntity>::class
    )
    @GetMapping
    fun getShipmentsByUserId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.PARAM_USER_ID, required = false) userId: String?
    ): ResponseEntity<List<ShipmentEntity>> {
        val shipments = this.getShipmentsUseCase.setLanguage(language).startCommand(userId)
        this.logger.info(GET_SHIPMENTS_BY_USER_ID_LOG, userId, shipments.joinToString())
        return ResponseEntity.ok(shipments)
    }

    @ApiOperation("Saves a new shipment into the database.")
    @ApiResponse(
        code = 200,
        message = "The shipment after being saved.",
        response = ShipmentEntityDto::class
    )
    @PostMapping
    fun saveNewShipment(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody(required = false) @Valid request: ShipmentEntityDto?
    ): ResponseEntity<Any> {
        val shipment = this.saveShipmentUseCase.setLanguage(language).startCommand(request)
        this.logger.info(SAVE_NEW_SHIPMENT_LOG, request?.userId, request)
        return ResponseEntity.ok(shipment)
    }

    @ApiOperation("Updates an existing shipment with the one provided.")
    @ApiResponse(
        code = 200,
        message = "A message indicating that the update operation was performed successfully.",
        response = ApiResult::class
    )
    @PutMapping
    fun updateExistingShipment(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody(required = false) @Valid request: ShipmentEntityDto?
    ): ResponseEntity<ApiResult> {
        val result = this.updateShipmentUseCase.setLanguage(language).startCommand(request)
        this.logger.info(UPDATE_SHIPMENT_LOG, request?.userId, request)
        return ResponseEntity.ok(result)
    }

}