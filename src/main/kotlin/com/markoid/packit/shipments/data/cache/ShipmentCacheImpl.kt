package com.markoid.packit.shipments.data.cache

import com.markoid.packit.core.data.cache.BaseCacheImpl
import com.markoid.packit.shipments.data.entities.ShipmentEntity

/**
 * This is the shipment cache, that will be saved against [userId] property
 */
class ShipmentCacheImpl : BaseCacheImpl<List<ShipmentEntity>, String>() {

    override fun deleteById(id: String): List<ShipmentEntity>? {
        return this.cache.remove(id)
    }

    override fun getById(id: String): List<ShipmentEntity>? {
        return this.cache[id]
    }

    override fun saveOrUpdate(id: String, entity: List<ShipmentEntity>): List<ShipmentEntity> {
        this.cache[id] = entity
        return entity
    }

}