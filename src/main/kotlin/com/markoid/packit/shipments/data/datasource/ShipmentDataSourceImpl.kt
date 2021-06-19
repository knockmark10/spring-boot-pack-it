package com.markoid.packit.shipments.data.datasource

import com.markoid.packit.shipments.data.cache.ShipmentCacheImpl
import com.markoid.packit.shipments.data.dao.ShipmentDao
import com.markoid.packit.shipments.data.entities.ShipmentEntity

class ShipmentDataSourceImpl(
    private val shipmentCache: ShipmentCacheImpl,
    private val shipmentDao: ShipmentDao
) : ShipmentDataSource {

    override fun cacheShipment(shipment: ShipmentEntity): ShipmentEntity {
        return this.shipmentCache.save(shipment)
    }

    override fun fetchAllShipments(): List<ShipmentEntity> {
        return this.shipmentDao.findAll()
    }

    override fun saveShipmentInDatabase(shipment: ShipmentEntity): ShipmentEntity {
        return this.shipmentDao.save(shipment)
    }

}