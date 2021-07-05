package com.markoid.packit.shipments.data.services

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.domain.usecases.requests.DeleteShipmentDto
import com.markoid.packit.shipments.domain.usecases.requests.ShipmentEntityDto
import com.markoid.packit.shipments.domain.usecases.results.GenerateIdResult
import org.springframework.web.multipart.MultipartFile

interface ShipmentService {
    fun deleteShipment(request: DeleteShipmentDto?): ApiResult
    fun generateShipmentId(): GenerateIdResult
    fun getShipmentsByUserId(userId: String?): List<ShipmentEntity>
    fun saveNewShipment(request: ShipmentEntityDto?): ShipmentEntity
    fun sendEmail(email: String?, file: MultipartFile?): ApiResult
    fun updateExistingShipment(request: ShipmentEntityDto?): ApiResult
}