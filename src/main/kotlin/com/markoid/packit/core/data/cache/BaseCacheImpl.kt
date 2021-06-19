package com.markoid.packit.core.data.cache

abstract class BaseCacheImpl<Entity, ID> : BaseCache<Entity, ID> {

    internal val cache: LinkedHashMap<ID, Entity> = LinkedHashMap()

}