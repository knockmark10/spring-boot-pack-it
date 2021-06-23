package com.markoid.packit.shipments.data.dao

import com.markoid.packit.shipments.data.entities.ShipmentEntity
import org.springframework.data.mongodb.repository.DeleteQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ShipmentDao : MongoRepository<ShipmentEntity, String> {

    @Query("{ 'userId': ?0 }")
    fun findShipmentByUserId(userId: String): List<ShipmentEntity>?

    @Query("{ 'shipId': ?0 }")
    fun findShipmentByShipId(shipId: String): ShipmentEntity?

    @DeleteQuery("{ '_id': ?0 }")
    fun deleteShipmentById(shipId: String): ShipmentEntity?

}