package com.markoid.packit.tracking.presentation.controllers

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.domain.usecases.*
import com.markoid.packit.tracking.domain.usecases.request.*
import com.markoid.packit.tracking.domain.usecases.results.GetAttachedTripResult
import com.markoid.packit.tracking.domain.usecases.results.TripResult
import com.markoid.packit.tracking.domain.usecases.results.UpdateTripRequest
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
        const val GET_LAST_LOCATION_LOG = "Requested last location with response: \n{}"
    }

    @PostMapping(ApiConstants.ATTACH_TRACKER_URL)
    fun attachTracker(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody @Valid body: AttachTrackerDto?
    ): ResponseEntity<ApiResult> {
        val result = this.attachTrackerUseCase.setLanguage(language).startCommand(body)
        this.logger.info(ATTACH_TRACKER_LOG, body)
        return ResponseEntity.ok(result)
    }

    @PostMapping(ApiConstants.BROADCAST_LOCATION_URL)
    fun broadcastLocation(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody @Valid body: BroadcastLocationDto?
    ): ResponseEntity<ApiResult> {
        val result = this.broadcastLocationUseCase.setLanguage(language).startCommand(body)
        this.logger.info(BROADCAST_LOCATION_LOG, body)
        return ResponseEntity.ok(result)
    }

    @PostMapping(ApiConstants.CREATE_TRIP_URL)
    fun createNewTrip(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody body: CreateNewTripRequest?
    ): ResponseEntity<TripEntity> = this.createNewTripUseCase.setLanguage(language).startCommand(body)

    @GetMapping(ApiConstants.GET_ACTIVE_TRIP_BY_DRIVER_ID_URL)
    fun getActiveTripByDriverId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestParam("driverId") driverId: String?
    ): ResponseEntity<TripResult> = this.getActiveTripByDriverIdUseCase.setLanguage(language).startCommand(driverId)

    @GetMapping(ApiConstants.GET_ATTACHED_SHIPMENT_URL)
    fun getAttachedShipment(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestParam(ApiConstants.PARAM_USER_ID) userId: String?
    ): ResponseEntity<Map<String, String>> = this.getAttachedShipmentUseCase.setLanguage(language).startCommand(userId)

    @GetMapping(ApiConstants.GET_ATTACHED_TRIP_URL)
    fun getAttachedTrip(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestParam("userId") userId: String?
    ): ResponseEntity<GetAttachedTripResult> = this.getAttachedTripUseCase.setLanguage(language).startCommand(userId)

    @PostMapping(ApiConstants.GET_LAST_LOCATION_URL)
    fun getLastLocation(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody(required = false) body: GetLastLocationDto?
    ): ResponseEntity<HistoryEntity> {
        val result = this.getLastLocationUseCase.setLanguage(language).startCommand(body)
        this.logger.info(GET_LAST_LOCATION_LOG, result)
        return ResponseEntity.ok(result)
    }

    @PutMapping(ApiConstants.UPDATE_SHIPMENT_STATUS_URL)
    fun updateShipmentStatus(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody body: UpdateShipmentStatusRequest
    ): ResponseEntity<BaseResponse> = this.updateShipmentStatusUseCase.setLanguage(language).startCommand(body)

    @PutMapping(ApiConstants.UPDATE_TRIP_STATUS_URL)
    fun updateTripStatus(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody body: UpdateTripRequest?
    ): ResponseEntity<BaseResponse> = this.updateTripStatusUseCase.setLanguage(language).startCommand(body)

}