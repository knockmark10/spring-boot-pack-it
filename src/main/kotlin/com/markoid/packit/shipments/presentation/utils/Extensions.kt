package com.markoid.packit.shipments.presentation.utils

fun String?.isNotNullOrEmpty(): Boolean =
    this.isNullOrEmpty().not()