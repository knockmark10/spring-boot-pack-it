package com.markoid.packit.shipments.data.datasource

import com.markoid.packit.shipments.data.entities.ShipmentEntity

interface ShipmentDataSource {
    fun cacheShipment(shipment: ShipmentEntity): ShipmentEntity
    fun fetchAllShipments(): List<ShipmentEntity>
    fun saveShipmentInDatabase(shipment: ShipmentEntity): ShipmentEntity
}