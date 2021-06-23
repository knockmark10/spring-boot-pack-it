package com.markoid.packit.shipments.data.datasource

import com.markoid.packit.shipments.data.entities.ShipmentEntity

interface ShipmentDataSource {
    fun cacheShipments(userId: String, shipments: List<ShipmentEntity>): List<ShipmentEntity>
    fun deleteShipmentById(shipId: String): ShipmentEntity?
    fun doesUserHaveShipments(userId: String): Boolean
    fun fetchShipmentFromDatabaseByShipId(shipId: String): ShipmentEntity?
    fun fetchShipmentsFromDatabaseByUserId(userId: String): List<ShipmentEntity>
    fun getCachedShipmentsByUserId(userId: String): List<ShipmentEntity>
    fun saveShipmentInDatabase(shipment: ShipmentEntity): ShipmentEntity
}