package com.markoid.packit.tracking.data.entities

import com.markoid.packit.shipments.data.entities.ShipmentStatus
import org.springframework.data.mongodb.core.mapping.Field

data class HistoryEntity(
    @Field("address")
    private val _address: String?,
    @Field("city")
    private val _city: String?,
    @Field("date")
    private val _date: String?,
    @Field("latitude")
    private val _latitude: Double?,
    @Field("longitude")
    private val _longitude: Double?,
    @Field("shipmentStatus")
    private val _shipmentStatus: ShipmentStatus?,
    @Field("state")
    private val _state: String?,
    @Field("userId")
    private val _userId: String?
) {

    val address: String
        get() = _address ?: ""

    val city: String
        get() = _city ?: ""

    val date: String
        get() = _date ?: ""

    val latitude: Double
        get() = _latitude ?: 0.0

    val longitude: Double
        get() = _longitude ?: 0.0

    val shipmentStatus: ShipmentStatus
        get() = _shipmentStatus ?: ShipmentStatus.Idle

    val state: String
        get() = _state ?: ""

    val userId: String
        get() = _userId ?: ""

}