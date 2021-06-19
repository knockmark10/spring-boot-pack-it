package com.markoid.packit.shipments.data.cache

import com.markoid.packit.core.data.cache.BaseCacheImpl
import com.markoid.packit.shipments.data.entities.ShipmentEntity

class ShipmentCacheImpl : BaseCacheImpl<List<ShipmentEntity>, String>() {

    override fun deleteById(id: String): List<ShipmentEntity>? {
        return this.cache.remove(id)
    }

    override fun getById(id: String): List<ShipmentEntity>? {
        return this.cache[id]
    }

    override fun saveOrUpdate(key: String, entity: List<ShipmentEntity>): List<ShipmentEntity> {
        this.cache[key] = entity
        return entity
    }

}