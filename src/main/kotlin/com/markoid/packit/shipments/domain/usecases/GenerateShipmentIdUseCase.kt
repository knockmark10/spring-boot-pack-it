package com.markoid.packit.shipments.domain.usecases

import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.shipments.domain.usecases.results.GenerateIdResult
import org.bson.types.ObjectId

class GenerateShipmentIdUseCase : AbstractUseCase<GenerateIdResult, Unit>() {

    override fun onExecuteTask(params: Unit): GenerateIdResult = GenerateIdResult(ObjectId.get().toHexString())

}