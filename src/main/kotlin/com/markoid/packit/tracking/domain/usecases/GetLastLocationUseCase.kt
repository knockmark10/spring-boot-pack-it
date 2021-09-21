package com.markoid.packit.tracking.domain.usecases

import com.markoid.packit.core.domain.usecases.AbstractUseCase
import com.markoid.packit.core.presentation.handlers.MessageDictionary.TRIP_ATTACHMENTS_NOT_FOUND
import com.markoid.packit.core.presentation.handlers.MessageDictionary.TRIP_NOT_FOUND
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.domain.usecases.request.GetLastLocationDto
import java.time.Instant

class GetLastLocationUseCase(
    private val trackingRepository: TrackingRepository
) : AbstractUseCase<HistoryEntity, GetLastLocationDto>() {

    override fun onExecuteTask(params: GetLastLocationDto): HistoryEntity {
        val trip = this.trackingRepository.getTripById(params.tripId)
            ?: throw raiseException(TRIP_NOT_FOUND)

        // Validate attachments
        if (trip.attachments.isEmpty()) throw raiseException(TRIP_ATTACHMENTS_NOT_FOUND)

        val userAttachments = trip.attachments.firstOrNull { it.userId == params.userId }
            ?: throw raiseException(TRIP_ATTACHMENTS_NOT_FOUND)

        // Return the first history in the list (which should be the last). Otherwise, return an empty history
        return userAttachments.historyList.lastOrNull() ?: HistoryEntity(date = Instant.now().toString())
    }

}