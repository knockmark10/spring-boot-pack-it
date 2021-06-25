package com.markoid.packit.shipments.data.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class PackageEntity(
    val description: String,
    val items: List<PackageItemEntity>,
    val name: String,
    @JsonProperty("packageId")
    val id: String = "",
    val status: PackageStatus,
    val color: PackageColors
)