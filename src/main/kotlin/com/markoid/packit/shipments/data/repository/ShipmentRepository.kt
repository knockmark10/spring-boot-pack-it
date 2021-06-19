package com.markoid.packit.shipments.data.repository

import com.markoid.packit.shipments.data.entities.ShipmentEntity

interface ShipmentRepository {
    fun getShipmentsByUserId(userId: String): List<ShipmentEntity>
    fun saveShipment(userId: String, shipment: ShipmentEntity): ShipmentEntity
}