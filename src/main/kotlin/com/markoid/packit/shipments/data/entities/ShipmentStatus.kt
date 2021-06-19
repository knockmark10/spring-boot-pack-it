package com.markoid.packit.shipments.data.entities

enum class ShipmentStatus {
    // Default status when created
    Idle,

    // When the packages are being collected
    PickingUp,

    // The delivery is on its way to the end user
    InTransitToDestination,

    // The shipment has reached its destination
    Delivered
}