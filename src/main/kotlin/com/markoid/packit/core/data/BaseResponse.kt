package com.markoid.packit.core.data

data class BaseResponse(
    val status: ApiState,
    val details: String
)