package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.core.presentation.handlers.ExceptionDictionary.*
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.GetLastLocationRequest
import org.joda.time.DateTime

class GetLastLocationUseCase(
    private val trackingRepository: TrackingRepository
) : BaseUseCase<HistoryEntity, GetLastLocationRequest>() {

    override fun onValidateRequest(request: GetLastLocationRequest): ValidationStatus = when {
        request.tripId.isNullOrEmpty() || request.userId.isNullOrEmpty() ->
            ValidationStatus.Failure(MISSING_PARAMETERS)
        else -> ValidationStatus.Success
    }

    override fun postValidatedExecution(request: GetLastLocationRequest): HistoryEntity {
        val trip = this.trackingRepository.getTripById(request.tripId!!)
            ?: throw raiseException(TRIP_NOT_FOUND)

        // Validate attachments
        if (trip.attachments.isEmpty()) throw raiseException(TRIP_ATTACHMENTS_NOT_FOUND)

        val userAttachments = trip.attachments.firstOrNull { it.userId == request.userId }
            ?: throw raiseException(TRIP_ATTACHMENTS_NOT_FOUND)

        // Return the first history in the list (which should be the last). Otherwise, return an empty history
        return userAttachments.historyList.lastOrNull() ?: HistoryEntity(date = DateTime(0).toString())
    }

}