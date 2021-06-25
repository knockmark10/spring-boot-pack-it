package com.markoid.packit.shipments.data.repository

import com.markoid.packit.shipments.data.entities.ShipmentEntity

interface ShipmentRepository {
    fun deleteShipmentByUserId(shipId: String, userId: String): Boolean
    fun getShipmentByShipId(shipId: String): ShipmentEntity?
    fun getShipmentsByUserId(userId: String): List<ShipmentEntity>
    fun saveNewShipment(userId: String, shipment: ShipmentEntity): ShipmentEntity
    fun updateExistingShipment(userId: String, shipmentToUpdate: ShipmentEntity): Boolean
}