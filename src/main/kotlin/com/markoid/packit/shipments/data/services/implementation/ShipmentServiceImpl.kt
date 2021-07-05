package com.markoid.packit.shipments.data.services.implementation

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.services.ShipmentService
import com.markoid.packit.shipments.domain.usecases.*
import com.markoid.packit.shipments.domain.usecases.requests.DeleteShipmentDto
import com.markoid.packit.shipments.domain.usecases.requests.SendMailDto
import com.markoid.packit.shipments.domain.usecases.requests.ShipmentEntityDto
import com.markoid.packit.shipments.domain.usecases.results.GenerateIdResult
import com.markoid.packit.shipments.presentation.controllers.ShipmentController
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ShipmentServiceImpl(
    private val deleteShipmentUseCase: DeleteShipmentUseCase,
    private val generateShipmentIdUseCase: GenerateShipmentIdUseCase,
    private val getShipmentsUseCase: GetShipmentsUseCase,
    private val saveShipmentUseCase: SaveShipmentUseCase,
    private val sendShipmentMailUseCase: SendShipmentMailUseCase,
    private val updateShipmentUseCase: UpdateShipmentUseCase
) : ShipmentService {

    private val logger = LoggerFactory.getLogger(ShipmentController::class.java)

    companion object {
        const val DELETE_SHIPMENT_LOG = "Shipment with id: {} from user: {} was deleted."
        const val GENERATE_SHIPMENT_ID_LOG = "New shipment id generated: {}"
        const val GET_SHIPMENTS_BY_USER_ID_LOG = "User with id {} have requested a list of shipments: {}"
        const val SAVE_NEW_SHIPMENT_LOG = "User with id {} has saved shipment {}"
        const val SEND_EMAIL_LOG = "Shipment report has been sent to {}"
        const val UPDATE_SHIPMENT_LOG = "User with id {} has updated his shipment {}"
    }

    override fun deleteShipment(request: DeleteShipmentDto?): ApiResult {
        val result = this.deleteShipmentUseCase.startCommand(request)
        this.logger.info(DELETE_SHIPMENT_LOG, request?.shipId, request?.userId)
        return result
    }

    override fun generateShipmentId(): GenerateIdResult {
        val generatedId = this.generateShipmentIdUseCase.startCommand(Unit)
        this.logger.info(GENERATE_SHIPMENT_ID_LOG, generatedId)
        return generatedId
    }

    override fun getShipmentsByUserId(userId: String?): List<ShipmentEntity> {
        val shipments = this.getShipmentsUseCase.startCommand(userId)
        this.logger.info(GET_SHIPMENTS_BY_USER_ID_LOG, userId, shipments.joinToString())
        return shipments
    }

    override fun saveNewShipment(request: ShipmentEntityDto?): ShipmentEntity {
        val shipment = this.saveShipmentUseCase.startCommand(request)
        this.logger.info(SAVE_NEW_SHIPMENT_LOG, request?.userId, request)
        return shipment
    }

    override fun sendEmail(email: String?, file: MultipartFile?): ApiResult {
        val request = SendMailDto(email, file)
        val result = this.sendShipmentMailUseCase.startCommand(request)
        this.logger.info(SEND_EMAIL_LOG, email)
        return result
    }

    override fun updateExistingShipment(request: ShipmentEntityDto?): ApiResult {
        val result = this.updateShipmentUseCase.startCommand(request)
        this.logger.info(UPDATE_SHIPMENT_LOG, request?.userId, request)
        return result
    }

}