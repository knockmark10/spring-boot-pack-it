package com.markoid.packit.tracking.data.services.abstraction

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.domain.usecases.request.*
import com.markoid.packit.tracking.domain.usecases.results.GetAttachedTripResult
import com.markoid.packit.tracking.domain.usecases.results.TripResult
import com.markoid.packit.tracking.domain.usecases.results.UpdateTripDto

interface TrackingService {
    fun attachTracker(request: AttachTrackerDto?): ApiResult
    fun broadcastLocation(request: BroadcastLocationDto?): ApiResult
    fun createNewTrip(request: CreateNewTripDto?): TripEntity
    fun getActiveTripByDriverId(driverId: String?): TripResult
    fun getAttachedShipment(userId: String?): Map<String, String>
    fun getAttachedTrip(userId: String?): GetAttachedTripResult
    fun getLastLocation(body: GetLastLocationDto?): HistoryEntity
    fun updateShipmentStatus(body: UpdateShipmentStatusDto?): ApiResult
    fun updateTripStatus(body: UpdateTripDto?): ApiResult
}