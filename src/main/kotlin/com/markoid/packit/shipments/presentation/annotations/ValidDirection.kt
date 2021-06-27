package com.markoid.packit.shipments.presentation.annotations

import com.markoid.packit.shipments.presentation.utils.DirectionEntityValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [DirectionEntityValidator::class])
@Target(
    allowedTargets = [
        AnnotationTarget.FUNCTION,
        AnnotationTarget.FIELD,
        AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.CONSTRUCTOR,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.TYPE_PARAMETER]
)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidDirection(
    val message: String = "Parameter is not complete.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)