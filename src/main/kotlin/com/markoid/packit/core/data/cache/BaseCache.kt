package com.markoid.packit.core.data.cache

interface BaseCache<Entity, ID> {
    fun save(entity: Entity): Entity
    fun delete(entity: Entity): Entity?
    fun deleteById(id: ID): Entity?
    fun getOne(id: ID): Entity?
}