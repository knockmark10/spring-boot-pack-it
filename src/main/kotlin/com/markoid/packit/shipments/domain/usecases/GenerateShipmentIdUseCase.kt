package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.shipments.domain.usecases.results.GenerateIdResult
import org.bson.types.ObjectId

class GenerateShipmentIdUseCase : BaseUseCase<GenerateIdResult, Unit>() {

    override fun postValidatedExecution(request: Unit): GenerateIdResult =
        GenerateIdResult(ObjectId.get().toHexString())

}