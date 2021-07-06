package com.markoid.packit.shipments.presentation.controllers

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.services.implementation.ShipmentServiceImpl
import com.markoid.packit.shipments.domain.usecases.*
import com.markoid.packit.shipments.domain.usecases.requests.DeleteShipmentDto
import com.markoid.packit.shipments.domain.usecases.requests.ShipmentEntityDto
import com.markoid.packit.shipments.domain.usecases.results.GenerateIdResult
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

/**
 * This is the controller responsible for all the operations supported for shipments.
 */
@RequestMapping(ApiConstants.SHIPMENT_PATH)
@RestController
class ShipmentController(private val shipmentService: ShipmentServiceImpl) {

    @ApiOperation("Deletes a shipment with the id provided.")
    @ApiResponse(
        code = 200,
        message = "A message indicating that the deletion was performed successfully.",
        response = ApiResult::class
    )
    @DeleteMapping
    fun deleteShipmentByUserId(@RequestBody @Valid body: DeleteShipmentDto?): ResponseEntity<ApiResult> {
        val result = this.shipmentService.deleteShipment(body)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("Generates a new shipment id to be used by client app.")
    @ApiResponse(
        code = 200,
        message = "The string ID generated",
        response = GenerateIdResult::class
    )
    @GetMapping(ApiConstants.GENERATE_SHIPMENT_ID)
    fun generateShipmentId(): ResponseEntity<GenerateIdResult> {
        val generatedId = this.shipmentService.generateShipmentId()
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
        @RequestHeader(ApiConstants.PARAM_USER_ID, required = false) userId: String?
    ): ResponseEntity<List<ShipmentEntity>> {
        val shipments = this.shipmentService.getShipmentsByUserId(userId)
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
        @RequestBody(required = false) @Valid request: ShipmentEntityDto?
    ): ResponseEntity<Any> {
        val shipment = this.shipmentService.saveNewShipment(request)
        return ResponseEntity.ok(shipment)
    }

    @ApiOperation("Send an email with the shipment PDF attached to it.")
    @ApiResponse(
        code = 200,
        message = "A message indicating that the email has been sent.",
        response = ShipmentEntityDto::class
    )
    @PostMapping(ApiConstants.SEND_EMAIL)
    fun sendEmail(
        @RequestParam("email", required = false) email: String?,
        @RequestParam("file") file: MultipartFile?
    ): ResponseEntity<Any> {
        // Create Dto
        val result = this.shipmentService.sendEmail(email, file)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("Updates an existing shipment with the one provided.")
    @ApiResponse(
        code = 200,
        message = "A message indicating that the update operation was performed successfully.",
        response = ApiResult::class
    )
    @PutMapping
    fun updateExistingShipment(
        @RequestBody(required = false) @Valid request: ShipmentEntityDto?
    ): ResponseEntity<ApiResult> {
        val result = this.shipmentService.updateExistingShipment(request)
        return ResponseEntity.ok(result)
    }

}