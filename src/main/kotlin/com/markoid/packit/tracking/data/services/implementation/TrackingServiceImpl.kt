package com.markoid.packit.tracking.data.services.implementation

import com.markoid.packit.core.data.ApiResult
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.entities.TripEntity
import com.markoid.packit.tracking.data.services.abstraction.TrackingService
import com.markoid.packit.tracking.domain.usecases.*
import com.markoid.packit.tracking.domain.usecases.request.*
import com.markoid.packit.tracking.domain.usecases.results.GetAttachedTripResult
import com.markoid.packit.tracking.domain.usecases.results.TripResult
import com.markoid.packit.tracking.domain.usecases.results.UpdateTripDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TrackingServiceImpl(
    private val attachTrackerUseCase: AttachTrackerUseCase,
    private val broadcastLocationUseCase: BroadcastLocationUseCase,
    private val createNewTripUseCase: CreateNewTripUseCase,
    private val getActiveTripByDriverIdUseCase: GetActiveTripByDriveIdUseCase,
    private val getAttachedShipmentUseCase: GetAttachedShipmentUseCase,
    private val getAttachedTripUseCase: GetAttachedTripUseCase,
    private val getLastLocationUseCase: GetLastLocationUseCase,
    private val updateShipmentStatusUseCase: UpdateShipmentStatusUseCase,
    private val updateTripStatusUseCase: UpdateTripStatusUseCase
) : TrackingService {

    private val logger = LoggerFactory.getLogger(TrackingServiceImpl::class.java)

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

    override fun attachTracker(request: AttachTrackerDto?): ApiResult {
        val result = this.attachTrackerUseCase.startCommand(request)
        this.logger.info(ATTACH_TRACKER_LOG, request)
        return result
    }

    override fun broadcastLocation(request: BroadcastLocationDto?): ApiResult {
        val result = this.broadcastLocationUseCase.startCommand(request)
        this.logger.info(BROADCAST_LOCATION_LOG, request)
        return result
    }

    override fun createNewTrip(request: CreateNewTripDto?): TripEntity {
        val result = this.createNewTripUseCase.startCommand(request)
        this.logger.info(CREATE_TRIP_LOG, result)
        return result
    }

    override fun getActiveTripByDriverId(driverId: String?): TripResult {
        val result = this.getActiveTripByDriverIdUseCase.startCommand(driverId)
        this.logger.info(GET_ACTIVE_TRIP_LOG, result)
        return result
    }

    override fun getAttachedShipment(userId: String?): Map<String, String> {
        val result = this.getAttachedShipmentUseCase.startCommand(userId)
        this.logger.info(GET_ATTACHED_SHIPMENT_LOG, result)
        return result
    }

    override fun getAttachedTrip(userId: String?): GetAttachedTripResult {
        val result = this.getAttachedTripUseCase.startCommand(userId)
        this.logger.info(GET_ATTACHED_TRIP_LOG, result)
        return result
    }

    override fun getLastLocation(body: GetLastLocationDto?): HistoryEntity {
        val result = this.getLastLocationUseCase.startCommand(body)
        this.logger.info(GET_LAST_LOCATION_LOG, result)
        return result
    }

    override fun updateShipmentStatus(body: UpdateShipmentStatusDto?): ApiResult {
        val result = this.updateShipmentStatusUseCase.startCommand(body)
        this.logger.info(UPDATE_SHIPMENT_STATUS, body?.shipmentStatus)
        return result
    }

    override fun updateTripStatus(body: UpdateTripDto?): ApiResult {
        val result = this.updateTripStatusUseCase.startCommand(body)
        this.logger.info(UPDATE_TRIP_STATUS_LOG, body?.status)
        return result
    }

}