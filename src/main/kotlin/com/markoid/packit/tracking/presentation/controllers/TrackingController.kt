package com.markoid.packit.tracking.presentation.controllers

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.domain.usecases.*
import com.markoid.packit.tracking.domain.usecases.request.*
import com.markoid.packit.tracking.domain.usecases.results.GetAttachedTripResult
import com.markoid.packit.tracking.domain.usecases.results.TripResult
import com.markoid.packit.tracking.domain.usecases.results.UpdateTripDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping(ApiConstants.TRACKING_PATH)
@RestController
class TrackingController(
    private val attachTrackerUseCase: AttachTrackerUseCase,
    private val broadcastLocationUseCase: BroadcastLocationUseCase,
    private val createNewTripUseCase: CreateNewTripUseCase,
    private val getActiveTripByDriverIdUseCase: GetActiveTripByDriveIdUseCase,
    private val getAttachedShipmentUseCase: GetAttachedShipmentUseCase,
    private val getAttachedTripUseCase: GetAttachedTripUseCase,
    private val getLastLocationUseCase: GetLastLocationUseCase,
    private val updateShipmentStatusUseCase: UpdateShipmentStatusUseCase,
    private val updateTripStatusUseCase: UpdateTripStatusUseCase
) {

    private val logger = LoggerFactory.getLogger(TrackingController::class.java)

    companion object {
        const val ATTACH_TRACKER_LOG = "Tracker has been attached.\n{}"
        const val BROADCAST_LOCATION_LOG = "Location has been broadcasted with data: \n{}"
        const val CREATE_TRIP_LOG = "New trip was created with data: \n{}"
        const val GET_ACTIVE_TRIP_LOG = "Active trip requested with data: \n{}"
        const val GET_ATTACHED_SHIPMENT_LOG = "Attached shipment requested: \n{}"
        const val GET_ATTACHED_TRIP_LOG = "Attached trip requested: \n{}"
        const val GET_LAST_LOCATION_LOG = "Requested last location with response: \n{}"
        const val UPDATE_SHIPMENT_STATUS = "Shipment status has been updated: {}"
        const val UPDATE_TRIP_STATUS_LOG = "Trip status updated to: {}"
    }

    @PostMapping(ApiConstants.ATTACH_TRACKER_URL)
    fun attachTracker(
        @RequestBody(required = false) @Valid body: AttachTrackerDto?
    ): ResponseEntity<ApiResult> {
        val result = this.attachTrackerUseCase.startCommand(body)
        this.logger.info(ATTACH_TRACKER_LOG, body)
        return ResponseEntity.ok(result)
    }

    @PostMapping(ApiConstants.BROADCAST_LOCATION_URL)
    fun broadcastLocation(
        @RequestBody(required = false) @Valid body: BroadcastLocationDto?
    ): ResponseEntity<ApiResult> {
        val result = this.broadcastLocationUseCase.startCommand(body)
        this.logger.info(BROADCAST_LOCATION_LOG, body)
        return ResponseEntity.ok(result)
    }

    @PostMapping(ApiConstants.CREATE_TRIP_URL)
    fun createNewTrip(
        @RequestBody body: CreateNewTripDto?
    ): ResponseEntity<TripEntity> {
        val result = this.createNewTripUseCase.startCommand(body)
        this.logger.info(CREATE_TRIP_LOG, result)
        return ResponseEntity.ok(result)
    }

    @GetMapping(ApiConstants.GET_ACTIVE_TRIP_BY_DRIVER_ID_URL)
    fun getActiveTripByDriverId(
        @RequestParam("driverId") driverId: String?
    ): ResponseEntity<TripResult> {
        val result = this.getActiveTripByDriverIdUseCase.startCommand(driverId)
        this.logger.info(GET_ACTIVE_TRIP_LOG, result)
        return ResponseEntity.ok(result)
    }

    @GetMapping(ApiConstants.GET_ATTACHED_SHIPMENT_URL)
    fun getAttachedShipment(
        @RequestParam(ApiConstants.PARAM_USER_ID_UPPER_CASE) userId: String?
    ): ResponseEntity<Map<String, String>> {
        val result = this.getAttachedShipmentUseCase.startCommand(userId)
        this.logger.info(GET_ATTACHED_SHIPMENT_LOG, result)
        return ResponseEntity.ok(result)
    }

    @GetMapping(ApiConstants.GET_ATTACHED_TRIP_URL)
    fun getAttachedTrip(
        @RequestParam(ApiConstants.PARAM_USER_ID_UPPER_CASE) userId: String?
    ): ResponseEntity<GetAttachedTripResult> {
        val result = this.getAttachedTripUseCase.startCommand(userId)
        this.logger.info(GET_ATTACHED_TRIP_LOG, result)
        return ResponseEntity.ok(result)
    }

    @PostMapping(ApiConstants.GET_LAST_LOCATION_URL)
    fun getLastLocation(
        @RequestBody(required = false) @Valid body: GetLastLocationDto?
    ): ResponseEntity<HistoryEntity> {
        val result = this.getLastLocationUseCase.startCommand(body)
        this.logger.info(GET_LAST_LOCATION_LOG, result)
        return ResponseEntity.ok(result)
    }

    @PutMapping(ApiConstants.UPDATE_SHIPMENT_STATUS_URL)
    fun updateShipmentStatus(
        @RequestBody(required = false) @Valid body: UpdateShipmentStatusDto?
    ): ResponseEntity<ApiResult> {
        val result = this.updateShipmentStatusUseCase.startCommand(body)
        this.logger.info(UPDATE_SHIPMENT_STATUS, body?.shipmentStatus)
        return ResponseEntity.ok(result)
    }

    @PutMapping(ApiConstants.UPDATE_TRIP_STATUS_URL)
    fun updateTripStatus(
        @RequestBody(required = false) @Valid body: UpdateTripDto?
    ): ResponseEntity<ApiResult> {
        val result = this.updateTripStatusUseCase.startCommand(body)
        this.logger.info(UPDATE_TRIP_STATUS_LOG, body?.status)
        return ResponseEntity.ok(result)
    }

}