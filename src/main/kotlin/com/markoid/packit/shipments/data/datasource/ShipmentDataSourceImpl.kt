package com.markoid.packit.shipments.data.datasource

import com.markoid.packit.shipments.data.cache.ShipmentCacheImpl
import com.markoid.packit.shipments.data.dao.ShipmentDao
import com.markoid.packit.shipments.data.entities.ShipmentEntity

class ShipmentDataSourceImpl(
    private val shipmentCache: ShipmentCacheImpl,
    private val shipmentDao: ShipmentDao
) : ShipmentDataSource {

    override fun cacheShipments(userId: String, shipments: List<ShipmentEntity>): List<ShipmentEntity> {
        return this.shipmentCache.saveOrUpdate(userId, shipments)
    }

    override fun fetchShipmentsFromDatabaseByUserId(userId: String): List<ShipmentEntity> {
        return this.shipmentDao.findShipmentByUserId(userId)
    }

    override fun getCachedShipmentsByUserId(userId: String): List<ShipmentEntity> {
        return this.shipmentCache.getById(userId) ?: emptyList()
    }

    override fun saveShipmentInDatabase(shipment: ShipmentEntity): ShipmentEntity {
        return this.shipmentDao.save(shipment)
    }

}