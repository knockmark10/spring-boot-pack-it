package com.markoid.packit.shipments.data.dao

import com.markoid.packit.shipments.data.entities.ShipmentEntity
import org.springframework.data.mongodb.repository.DeleteQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ShipmentDao : MongoRepository<ShipmentEntity, String> {

    @Query("{ 'userId': ?0 }")
    fun findShipmentsByUserId(userId: String): List<ShipmentEntity>?

    @Query("{ 'shipId': ?0 }")
    fun findShipmentByShipId(shipId: String): ShipmentEntity?

    @DeleteQuery("{ 'shipId': ?0 }")
    fun deleteShipmentByShipId(shipId: String): ShipmentEntity?

}