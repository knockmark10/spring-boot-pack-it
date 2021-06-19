package com.markoid.packit.shipments.data.repository

import com.markoid.packit.shipments.data.entities.ShipmentEntity

interface ShipmentRepository {
    fun getAllShipments(): List<ShipmentEntity>
    fun saveShipment(shipment: ShipmentEntity): ShipmentEntity
}