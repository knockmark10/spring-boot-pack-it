package com.markoid.packit.shipments.data.entities

data class PackageItemEntity(
    val id: String,
    val name: String,
    val quantity: Int,
    val image: String? = null
)