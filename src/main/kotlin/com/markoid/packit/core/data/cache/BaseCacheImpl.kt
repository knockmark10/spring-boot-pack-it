package com.markoid.packit.core.data.cache

abstract class BaseCacheImpl<Entity, ID> : BaseCache<Entity, ID> {

    val cache: LinkedHashMap<ID, Entity> = LinkedHashMap()

}