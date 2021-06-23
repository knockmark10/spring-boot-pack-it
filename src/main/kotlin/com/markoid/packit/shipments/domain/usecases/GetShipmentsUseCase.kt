package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary
import com.markoid.packit.shipments.data.entities.ShipmentEntity
import com.markoid.packit.shipments.data.repository.ShipmentRepository

class GetShipmentsUseCase(
    private val shipmentRepository: ShipmentRepository
) : BaseUseCase<List<ShipmentEntity>, String?>() {

    override fun onValidateRequest(request: String?): ValidationStatus =
        if (request.isNullOrEmpty()) ValidationStatus.Failure(ExceptionDictionary.MISSING_PARAMETERS)
        else ValidationStatus.Success

    /**
     * Get the shipments with the user id provided
     */
    override fun postValidatedExecution(request: String?): List<ShipmentEntity> =
        this.shipmentRepository.getShipmentsByUserId(request!!)

}