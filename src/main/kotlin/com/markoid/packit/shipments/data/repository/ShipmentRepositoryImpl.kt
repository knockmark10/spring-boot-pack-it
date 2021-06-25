package com.markoid.packit.shipments.data.repository

import com.markoid.packit.shipments.data.datasource.ShipmentDataSourceImpl
import com.markoid.packit.shipments.data.entities.ShipmentEntity

class ShipmentRepositoryImpl(
    private val shipmentDataSource: ShipmentDataSourceImpl
) : ShipmentRepository {

    override fun deleteShipmentByUserId(shipId: String, userId: String): Boolean {
        // Delete the shipment from the database
        this.shipmentDataSource.deleteShipmentByIdFromDatabase(shipId) ?: return false
        // Now remove it from cache
        this.shipmentDataSource.deleteCachedShipment(userId, shipId)
        // At this point both removals were performed successfully. Return true
        return true
    }

    override fun getShipmentByShipId(shipId: String): ShipmentEntity? {
        return this.shipmentDataSource.fetchShipmentFromDatabaseByShipId(shipId)
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
        this.shipmentDataSource.saveOrUpdateShipmentInDatabase(shipment)
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
     * @param shipmentToUpdate - The shipment to be updated
     * @return - Whether or not the update was performed
     */
    override fun updateExistingShipment(userId: String, shipmentToUpdate: ShipmentEntity): Boolean {
        val existingShipment = getShipmentByShipId(shipmentToUpdate.shipId) ?: return false
        // Update the shipment in database
        this.shipmentDataSource.saveOrUpdateShipmentInDatabase(shipmentToUpdate.copy(id = existingShipment.id))
        // Update the shipment in cache. To do that get the shipments from the cache EXCEPT the one we want to update.
        val cachedShipments = this.shipmentDataSource
            .getCachedShipmentsByUserId(userId)
            .filter { it.shipId != shipmentToUpdate.shipId }
            .toMutableList()
        // Then proceed to add the updated shipment to the cached list
        cachedShipments.add(shipmentToUpdate.copy(id = existingShipment.id))
        // Finally update the cache
        this.shipmentDataSource.cacheShipments(userId, cachedShipments)
        // Return true, meaning it was updated successfully
        return true
    }

}