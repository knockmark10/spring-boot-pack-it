package com.markoid.packit.tracking.domain.usecases

/*import com.markoid.packit.core.data.BaseResponse
import com.markoid.packit.core.domain.exceptions.IncompleteRequestException
import com.markoid.packit.core.domain.usecases.BaseUseCase
import com.markoid.packit.tracking.domain.usecases.request.AttachTrackerRequest
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity

class AttachTrackerUseCase(
    messageSource: MessageSource
) : BaseUseCase<BaseResponse, AttachTrackerRequest>(messageSource) {

    override fun execute(request: AttachTrackerRequest): ResponseEntity<BaseResponse> {

    }

    private fun validateRequest(request: AttachTrackerRequest, block: () -> Unit) = when {
        request.driverId.isEmpty() ||
                request.shipId.isEmpty() ||
                request.tripId.isEmpty() ||
                request.userId.isEmpty() -> throw IncompleteRequestException(getString("MISSING_PARAMETERS"))
        else -> block.invoke()
    }

}*/