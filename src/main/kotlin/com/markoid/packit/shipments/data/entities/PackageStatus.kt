package com.markoid.packit.shipments.data.entities

enum class PackageStatus {
    // Default status when container is created
    PACKING,

    // Container is packed and ready to be shipped
    PACKED,

    // Container was received
    DELIVERED
}