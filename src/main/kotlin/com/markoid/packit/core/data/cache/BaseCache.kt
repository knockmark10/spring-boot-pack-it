package com.markoid.packit.core.data.cache

interface BaseCache<Entity, ID> {
    fun deleteById(id: ID): Entity?
    fun getById(id: ID): Entity?
    fun saveOrUpdate(id: ID, entity: Entity): Entity
}