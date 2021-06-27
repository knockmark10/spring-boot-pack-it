package com.markoid.packit.shipments.presentation.utils

import com.markoid.packit.shipments.data.entities.DirectionEntity
import com.markoid.packit.shipments.presentation.annotations.ValidDirection
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DirectionEntityValidator : ConstraintValidator<ValidDirection, DirectionEntity> {

    override fun isValid(value: DirectionEntity?, context: ConstraintValidatorContext?): Boolean =
        value != null && isValidDirection(value)

    private fun isValidDirection(direction: DirectionEntity): Boolean = with(direction) {
        address.isNotEmpty() && city.isNotEmpty() && latitude != 0.0 && longitude != 0.0 && state.isNotEmpty()
    }

}