package com.markoid.packit.tracking.presentation.controllers

import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.domain.usecases.*
import com.markoid.packit.tracking.domain.usecases.request.AttachTrackerRequest
import com.markoid.packit.tracking.domain.usecases.request.BroadcastLocationRequest
import com.markoid.packit.tracking.domain.usecases.request.CreateNewTripRequest
import com.markoid.packit.tracking.domain.usecases.results.TripResult
import com.markoid.packit.tracking.domain.usecases.results.UpdateTripRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(ApiConstants.TRACKING_PATH)
@RestController
class TrackingController(
    private val attachTrackerUseCase: AttachTrackerUseCase,
    private val broadcastLocationUseCase: BroadcastLocationUseCase,
    private val createNewTripUseCase: CreateNewTripUseCase,
    private val getActiveTripByDriverIdUseCase: GetActiveTripByDriveIdUseCase,
    private val getAttachedShipmentUseCase: GetAttachedShipmentUseCase,
    private val updateTripStatusUseCase: UpdateTripStatusUseCase
) {

    @PostMapping(ApiConstants.ATTACH_TRACKER_URL)
    fun attachTracker(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody body: AttachTrackerRequest?
    ): ResponseEntity<BaseResponse> = this.attachTrackerUseCase.setLanguage(language).startCommand(body)

    @PostMapping(ApiConstants.BROADCAST_LOCATION_URL)
    fun broadcastLocation(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_SHIPMENT_ID, required = false) shipId: String? = null,
        @RequestBody body: BroadcastLocationRequest?
    ): ResponseEntity<BaseResponse> {
        val request = BroadcastLocationRequest(
            body?.address,
            body?.city,
            body?.date,
            body?.latitude,
            body?.longitude,
            body?.shipmentStatus,
            body?.state,
            shipId,
            body?.tripId,
            body?.userId
        )
        return this.broadcastLocationUseCase.setLanguage(language).startCommand(request)
    }

    @PostMapping(ApiConstants.CREATE_TRIP_URL)
    fun createNewTrip(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String,
        @RequestBody body: CreateNewTripRequest?
    ): ResponseEntity<TripEntity> = this.createNewTripUseCase
        .setLanguage(language)
        .startCommand(body?.copy(userId = userId))

    @GetMapping(ApiConstants.GET_ACTIVE_TRIP_BY_DRIVER_ID_URL)
    fun getActiveTripByDriverId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestParam("driverId") driverId: String?
    ): ResponseEntity<TripResult> = this.getActiveTripByDriverIdUseCase.setLanguage(language).startCommand(driverId)

    @GetMapping(ApiConstants.GET_ATTACHED_SHIPMENT_URL)
    fun getAttachedShipment(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestParam("userId") userId: String?
    ): ResponseEntity<Map<String, String>> = this.getAttachedShipmentUseCase.setLanguage(language).startCommand(userId)

    @PutMapping(ApiConstants.UPDATE_TRIP_STATUS_URL)
    fun updateTripStatus(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody body: UpdateTripRequest?
    ): ResponseEntity<BaseResponse> = this.updateTripStatusUseCase.setLanguage(language).startCommand(body)

}