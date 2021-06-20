package com.markoid.packit.shipments.data.repository

import com.markoid.packit.shipments.data.datasource.ShipmentDataSourceImpl
import com.markoid.packit.shipments.data.entities.ShipmentEntity

class ShipmentRepositoryImpl(
    private val shipmentDataSource: ShipmentDataSourceImpl
) : ShipmentRepository {

    override fun deleteShipmentById(shipId: String): Boolean {
        val shipmentDeleted = this.shipmentDataSource.deleteShipmentById(shipId)
        return shipmentDeleted != null
    }

    /**
     * Gets the user's shipments. Caching technique is implemented so that we have the cache always up-to-date and to
     * improve performance. Shipment will be looked up in the cache. If it's there, that shipment list will be retrieved.
     * Otherwise, a database lookup will be performed, and then will be cached to keep the cache updated.
     */
    override fun getShipmentsByUserId(userId: String): List<ShipmentEntity> {
        val shipments = mutableListOf<ShipmentEntity>()
        // Get the list from cache
        shipments.addAll(this.shipmentDataSource.getCachedShipmentsByUserId(userId))
        // Check shipment list. If it is empty, it's not cached. Retrieve it from the database
        if (shipments.isEmpty()) {
            // Get shipment from database
            shipments.addAll(this.shipmentDataSource.fetchShipmentsFromDatabaseByUserId(userId))
            // Cache shipment to keep it updated
            this.shipmentDataSource.cacheShipments(userId, shipments)
        }
        return shipments
    }

    /**
     * Saves the shipment into the database and caches it. This will make sure the cache is up-to-date, so that database
     * lookups are not required very often, and improve performance.
     */
    override fun saveNewShipment(userId: String, shipment: ShipmentEntity): ShipmentEntity {
        // Save shipment in database
        this.shipmentDataSource.saveShipmentInDatabase(shipment)
        // Cache shipments. Use SET to prevent duplications
        val cachedShipments = mutableSetOf<ShipmentEntity>()
        // Retrieve the cached shipments
        cachedShipments.addAll(this.shipmentDataSource.getCachedShipmentsByUserId(userId))
        // Add the new shipment to the list
        cachedShipments.add(shipment)
        // Cache the updated shipment list
        this.shipmentDataSource.cacheShipments(userId, cachedShipments.toList())
        // Return shipment
        return shipment
    }

    /**
     * Updates an existing shipment, if there is one.
     *
     * @param userId - Id of the shipment's owner
     * @param shipment - The shipment to be updated
     * @return - Whether or not the update was performed
     */
    override fun updateExistingShipment(userId: String, shipment: ShipmentEntity): Boolean {
        // Check if user has shipments
        return if (this.shipmentDataSource.doesUserHaveShipments(userId)) {
            // Update the shipment
            saveNewShipment(userId, shipment)
            true
        } else {
            false
        }
    }

}