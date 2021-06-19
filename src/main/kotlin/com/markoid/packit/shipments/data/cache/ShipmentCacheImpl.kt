package com.markoid.packit.shipments.data.cache

import com.markoid.packit.core.data.cache.BaseCacheImpl
import com.markoid.packit.shipments.data.entities.ShipmentEntity

class ShipmentCacheImpl : BaseCacheImpl<ShipmentEntity, String>() {

    override fun save(entity: ShipmentEntity): ShipmentEntity {
        this.cache[entity.shipId] = entity
        return entity
    }

    override fun delete(entity: ShipmentEntity): ShipmentEntity? {
        val wasDeleted = this.cache.remove(entity.shipId, entity)
        return if (wasDeleted) entity else null
    }

    override fun deleteById(id: String): ShipmentEntity? {
        return this.cache.remove(id)
    }

    override fun getOne(id: String): ShipmentEntity? {
        return this.cache[id]
    }

}