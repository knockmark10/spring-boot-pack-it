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

    override fun deleteShipmentByIdFromDatabase(shipId: String): ShipmentEntity? {
        return this.shipmentDao.deleteShipmentByShipId(shipId)
    }

    override fun deleteCachedShipment(userId: String, shipId: String) {
        this.shipmentCache.deleteShipment(userId, shipId)
    }

    override fun fetchShipmentFromDatabaseByShipId(shipId: String): ShipmentEntity? {
        return this.shipmentDao.findShipmentByShipId(shipId)
    }

    override fun fetchShipmentsFromDatabaseByUserId(userId: String): List<ShipmentEntity> {
        return this.shipmentDao.findShipmentsByUserId(userId) ?: emptyList()
    }

    override fun getCachedShipmentsByUserId(userId: String): List<ShipmentEntity> {
        return this.shipmentCache.getById(userId)
    }

    override fun saveOrUpdateShipmentInDatabase(shipment: ShipmentEntity): ShipmentEntity {
        return this.shipmentDao.save(shipment)
    }

}