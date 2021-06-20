package com.markoid.packit.core.presentation.utils

object ApiConstants {

    // Authentication constants
    const val KEY = "q3t6w9z\$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh"
    const val EXPIRATION_TIME = 1000 * 60 * 60 * 24

    const val HEADER_TOKEN = "x-access-token"
    const val HEADER_LANGUAGE = "language"
    const val HEADER_USER_ID = "userid"

    // PATHS
    private const val BASE_PATH = "/api/v2"
    const val AUTH_PATH = "$BASE_PATH/auth"
    const val SHIPMENT_PATH = "$BASE_PATH/shipments"
    const val TRACKING_PATH = "$BASE_PATH/tracking"

    // ENDPOINTS
    const val SIGN_IN_URL = "/signIn"
    const val SIGN_UP_URL = "/signUp"
    const val CREATE_TRIP_URL = "/createTrip"

}