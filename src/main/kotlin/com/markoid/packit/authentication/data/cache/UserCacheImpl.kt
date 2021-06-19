package com.markoid.packit.authentication.data.cache

import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.core.data.cache.BaseCacheImpl

class UserCacheImpl : BaseCacheImpl<UserEntity, String>() {

    override fun save(entity: UserEntity): UserEntity {
        this.cache[entity.email] = entity
        return entity
    }

    override fun delete(entity: UserEntity): UserEntity? {
        val wasDeleted = this.cache.remove(entity.email, entity)
        return if (wasDeleted) entity else null
    }

    override fun deleteById(id: String): UserEntity? {
        return this.cache.remove(id)
    }

    override fun getOne(id: String): UserEntity? {
        return this.cache[id]
    }

}