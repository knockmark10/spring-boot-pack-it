package com.markoid.packit.tracking.presentation.controllers

import com.markoid.packit.core.presentation.utils.ApiConstants
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(ApiConstants.SHIPMENT_PATH)
@RestController
class TrackingController(
    //private val createNewTripUseCase: CreateNewTripUseCase
) {

    /*@PostMapping(ApiConstants.CREATE_TRIP_URL)
    fun createNewTrip(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestHeader(ApiConstants.HEADER_USER_ID, required = false) userId: String,
        @RequestBody body: CreateNewTripRequest?
    ): ResponseEntity<TripEntity> = this.createNewTripUseCase.setLanguage(language).execute(body?.copy(userId = userId))*/

}