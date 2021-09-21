package com.markoid.packit.tracking.presentation.controllers

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.data.services.implementation.TrackingServiceImpl
import com.markoid.packit.tracking.domain.usecases.request.*
import com.markoid.packit.tracking.domain.usecases.results.GetAttachedTripResult
import com.markoid.packit.tracking.domain.usecases.results.TripResult
import com.markoid.packit.tracking.domain.usecases.results.UpdateTripDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping(ApiConstants.TRACKING_PATH)
@RestController
class TrackingController(private val trackingService: TrackingServiceImpl) {

    @PostMapping(ApiConstants.ATTACH_TRACKER_URL)
    fun attachTracker(
        @RequestBody(required = false) @Valid body: AttachTrackerDto?
    ): ResponseEntity<ApiResult> {
        val result = this.trackingService.attachTracker(body)
        return ResponseEntity.ok(result)
    }

    @PostMapping(ApiConstants.BROADCAST_LOCATION_URL)
    fun broadcastLocation(
        @RequestBody(required = false) @Valid body: BroadcastLocationDto?
    ): ResponseEntity<ApiResult> {
        val result = this.trackingService.broadcastLocation(body)
        return ResponseEntity.ok(result)
    }

    @PostMapping(ApiConstants.CREATE_TRIP_URL)
    fun createNewTrip(
        @RequestBody body: CreateNewTripDto?
    ): ResponseEntity<TripEntity> {
        val result = this.trackingService.createNewTrip(body)
        return ResponseEntity.ok(result)
    }

    @GetMapping(ApiConstants.GET_ACTIVE_TRIP_BY_DRIVER_ID_URL)
    fun getActiveTripByDriverId(
        @RequestParam("driverId") driverId: String?
    ): ResponseEntity<TripResult> {
        val result = this.trackingService.getActiveTripByDriverId(driverId)
        return ResponseEntity.ok(result)
    }

    @GetMapping(ApiConstants.GET_ATTACHED_SHIPMENT_URL)
    fun getAttachedShipment(
        @RequestParam(ApiConstants.PARAM_USER_ID_UPPER_CASE) userId: String?
    ): ResponseEntity<Map<String, String>> {
        val result = this.trackingService.getAttachedShipment(userId)
        return ResponseEntity.ok(result)
    }

    @GetMapping(ApiConstants.GET_ATTACHED_TRIP_URL)
    fun getAttachedTrip(
        @RequestParam(ApiConstants.PARAM_USER_ID_UPPER_CASE) userId: String?
    ): ResponseEntity<GetAttachedTripResult> {
        val result = this.trackingService.getAttachedTrip(userId)
        return ResponseEntity.ok(result)
    }

    @PostMapping(ApiConstants.GET_LAST_LOCATION_URL)
    fun getLastLocation(
        @RequestBody(required = false) @Valid body: GetLastLocationDto?
    ): ResponseEntity<HistoryEntity> {
        val result = this.trackingService.getLastLocation(body)
        return ResponseEntity.ok(result)
    }

    @PutMapping(ApiConstants.UPDATE_SHIPMENT_STATUS_URL)
    fun updateShipmentStatus(
        @RequestBody(required = false) @Valid body: UpdateShipmentStatusDto?
    ): ResponseEntity<ApiResult> {
        val result = this.trackingService.updateShipmentStatus(body)
        return ResponseEntity.ok(result)
    }

    @PutMapping(ApiConstants.UPDATE_TRIP_STATUS_URL)
    fun updateTripStatus(
        @RequestBody(required = false) @Valid body: UpdateTripDto?
    ): ResponseEntity<ApiResult> {
        val result = this.trackingService.updateTripStatus(body)
        return ResponseEntity.ok(result)
    }

}