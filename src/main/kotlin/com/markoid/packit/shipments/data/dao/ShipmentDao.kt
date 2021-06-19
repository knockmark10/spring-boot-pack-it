package com.markoid.packit.shipments.data.dao

import com.markoid.packit.shipments.data.entities.ShipmentEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface ShipmentDao: MongoRepository<ShipmentEntity, String> {
}