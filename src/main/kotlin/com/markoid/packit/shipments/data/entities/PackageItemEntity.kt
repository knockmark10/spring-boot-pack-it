package com.markoid.packit.shipments.data.entities

data class PackageItemEntity(
    val id: String,
    val name: String,
    val quantity: Int,
    private val _image: String? = null
)