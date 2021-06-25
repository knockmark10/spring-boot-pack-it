package com.markoid.packit.shipments.data.datasource

import com.markoid.packit.shipments.data.entities.ShipmentEntity

interface ShipmentDataSource {
    fun cacheShipments(userId: String, shipments: List<ShipmentEntity>): List<ShipmentEntity>
    fun deleteShipmentByIdFromDatabase(shipId: String): ShipmentEntity?
    fun deleteCachedShipment(userId: String, shipId: String)
    fun fetchShipmentFromDatabaseByShipId(shipId: String): ShipmentEntity?
    fun fetchShipmentsFromDatabaseByUserId(userId: String): List<ShipmentEntity>
    fun getCachedShipmentsByUserId(userId: String): List<ShipmentEntity>
    fun saveOrUpdateShipmentInDatabase(shipment: ShipmentEntity): ShipmentEntity
}