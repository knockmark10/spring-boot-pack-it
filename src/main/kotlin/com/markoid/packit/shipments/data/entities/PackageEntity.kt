package com.markoid.packit.shipments.data.entities

data class PackageEntity(
    val description: String,
    val items: List<PackageItemEntity>,
    val name: String,
    val id: String,
    val status: PackageStatus,
    val color: PackageColors
)