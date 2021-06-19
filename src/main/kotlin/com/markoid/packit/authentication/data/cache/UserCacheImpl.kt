package com.markoid.packit.authentication.data.cache

import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.core.data.cache.BaseCacheImpl

class UserCacheImpl : BaseCacheImpl<UserEntity, String>() {

    override fun deleteById(id: String): UserEntity? {
        return this.cache.remove(id)
    }

    override fun getById(id: String): UserEntity? {
        return this.cache[id]
    }

    override fun saveOrUpdate(id: String, entity: UserEntity): UserEntity {
        this.cache[entity.email] = entity
        return entity
    }

}