package com.markoid.packit.authentication.data.cache

import com.markoid.packit.authentication.data.entities.DriverEntity
import com.markoid.packit.core.data.cache.BaseCacheImpl

class DriverCacheImpl : BaseCacheImpl<DriverEntity, String>() {

    override fun deleteById(id: String): DriverEntity? {
        return this.cache.remove(id)
    }

    override fun getById(id: String): DriverEntity? {
        return this.cache[id]
    }

    override fun saveOrUpdate(id: String, entity: DriverEntity): DriverEntity {
        this.cache[entity.email] = entity
        return entity
    }

}