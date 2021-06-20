package com.markoid.packit.tracking.data.entities

enum class TripStatus {

    /**
     * This status is set by default when a new service gets created.
     *
     * When set back from [Active] to [Inactive], all shipment must be set to [ShipmentStatus.Idle].
     *
     * [LocationService] is stopped on this state.
     */
    Inactive,

    /**
     * Indicates that the driver is on duty.
     *
     * When this status is set, new shipments can be added and [ShipmentStatus] can be switched.
     *
     * Foreground service is active.
     */
    Active,

    /**
     * Indicates that the trip is over.
     *
     * All shipments must be set to [ShipmentStatus.Delivered]
     *
     * Foreground service is stopped on this state
     *
     * Service status must be set to false, to be able to start a new one
     */
    Archived,

    /**
     * Default status to indicate that there is no status provided from the client
     */
    Unknown

}