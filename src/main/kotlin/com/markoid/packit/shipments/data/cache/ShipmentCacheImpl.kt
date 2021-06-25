package com.markoid.packit.shipments.data.cache

import com.markoid.packit.core.data.cache.BaseCacheImpl
import com.markoid.packit.shipments.data.entities.ShipmentEntity

/**
 * This is the shipment cache, that will be saved against [userId] property
 */
class ShipmentCacheImpl : BaseCacheImpl<List<ShipmentEntity>, String>() {

    override fun deleteById(id: String): List<ShipmentEntity> {
        return this.cache.remove(id) ?: emptyList()
    }

    fun deleteShipment(userId: String, shipId: String) {
        // Filter out the shipment desired from the cached shipments
        val shipments = this.cache[userId]?.filter { it.shipId != shipId } ?: emptyList()

        // If the filtered list is empty, it means the user does not have any shipment left. Just remove it.
        if (shipments.isEmpty()) {
            deleteById(userId)
            return
        }

        // Finally update the shipment list with the filtered one
        this.cache[userId] = shipments
    }

    override fun getById(id: String): List<ShipmentEntity> {
        return this.cache[id] ?: emptyList()
    }

    override fun saveOrUpdate(id: String, entity: List<ShipmentEntity>): List<ShipmentEntity> {
        this.cache[id] = entity
        return entity
    }

}