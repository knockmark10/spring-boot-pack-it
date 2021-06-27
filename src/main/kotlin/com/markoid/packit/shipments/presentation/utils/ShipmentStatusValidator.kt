package com.markoid.packit.shipments.presentation.utils

import com.markoid.packit.shipments.data.entities.ShipmentStatus
import com.markoid.packit.shipments.presentation.annotations.ValidShipmentStatus
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ShipmentStatusValidator : ConstraintValidator<ValidShipmentStatus, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean =
        value != null && isValidShipmentStatus(value)

    private fun isValidShipmentStatus(status: String): Boolean =
        ShipmentStatus.values().firstOrNull { it.name == status } != null

}