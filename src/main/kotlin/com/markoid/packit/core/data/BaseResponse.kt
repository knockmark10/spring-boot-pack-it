package com.markoid.packit.core.data

@Deprecated("Deprecated in favor of ApiError, for a more detailed message.")
data class BaseResponse(
    val status: ApiState,
    val details: String
)