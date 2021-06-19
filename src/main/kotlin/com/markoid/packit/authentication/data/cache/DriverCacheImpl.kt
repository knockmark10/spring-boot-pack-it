package com.markoid.packit.authentication.data.cache

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.core.data.cache.BaseCacheImpl

class DriverCacheImpl : BaseCacheImpl<DriverEntity, String>() {

    override fun save(entity: DriverEntity): DriverEntity {
        this.cache[entity.email] = entity
        return entity
    }

    override fun delete(entity: DriverEntity): DriverEntity? {
        val wasDeleted = this.cache.remove(entity.email, entity)
        return if (wasDeleted) entity else null
    }

    override fun deleteById(id: String): DriverEntity? {
        return this.cache.remove(id)
    }

    override fun getOne(id: String): DriverEntity? {
        return this.cache[id]
    }

}