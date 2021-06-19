package com.markoid.packit.authentication.data.cache

import com.markoid.packit.authentication.data.entities.UserEntity
import com.markoid.packit.core.data.cache.BaseCache

interface UserCache : BaseCache<UserEntity, String>