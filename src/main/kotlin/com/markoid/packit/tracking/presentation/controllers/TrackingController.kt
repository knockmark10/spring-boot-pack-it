package com.markoid.packit.tracking.presentation.controllers

import com.markoid.packit.core.data.AppLanguage
import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.domain.usecases.AttachTrackerUseCase
import com.markoid.packit.tracking.domain.usecases.BroadcastLocationUseCase
import com.markoid.packit.tracking.domain.usecases.CreateNewTripUseCase
import com.markoid.packit.tracking.domain.usecases.request.AttachTrackerRequest
import com.markoid.packit.tracking.domain.usecases.request.BroadcastLocationRequest
import com.markoid.packit.tracking.domain.usecases.request.CreateNewTripRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(ApiConstants.TRACKING_PATH)
@RestController
class TrackingController(
    private val attachTrackerUseCase: AttachTrackerUseCase,
    private val broadcastLocationUseCase: BroadcastLocationUseCase,
    private val createNewTripUseCase: CreateNewTripUseCase
) {

    @PostMapping(ApiConstants.ATTACH_TRACKER_URL)
    fun attachTracker(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestBody body: AttachTrackerRequest?
    ): ResponseEntity<BaseResponse> = this.attachTrackerUseCase
        .setLanguage(AppLanguage.forValue(language))
        .startCommand(body)

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
        return this.broadcastLocationUseCase.setLanguage(AppLanguage.forValue(language)).startCommand(request)
    }

    @PostMapping(ApiConstants.CREATE_TRIP_URL)
    fun createNewTrip(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String,
        @RequestBody body: CreateNewTripRequest?
    ): ResponseEntity<TripEntity> = this.createNewTripUseCase
        .setLanguage(AppLanguage.forValue(language))
        .startCommand(body?.copy(userId = userId))

}