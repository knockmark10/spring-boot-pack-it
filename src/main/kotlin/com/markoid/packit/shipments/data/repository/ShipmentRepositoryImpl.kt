package com.markoid.packit.shipments.data.repository

import com.markoid.packit.shipments.data.datasource.ShipmentDataSourceImpl
import com.markoid.packit.shipments.data.entities.ShipmentEntity

class ShipmentRepositoryImpl(
    private val shipmentDataSource: ShipmentDataSourceImpl
) : ShipmentRepository {

    /**
     * Gets all shipments from database, and updates the cache
     */
    override fun getAllShipments(): List<ShipmentEntity> {
        // Fetch shipments from database
        val shipments = this.shipmentDataSource.fetchAllShipments()
        // Cache shipments
        shipments.forEach { this.shipmentDataSource.cacheShipment(it) }
        // Return shipment list
        return shipments
    }

    /**
     * Saves the shipment into the database and caches it
     */
    override fun saveShipment(shipment: ShipmentEntity): ShipmentEntity {
        // Save shipment in database
        this.shipmentDataSource.saveShipmentInDatabase(shipment)
        // Cache shipment
        this.shipmentDataSource.cacheShipment(shipment)
        // Return shipment
        return shipment
    }

}