package com.markoid.packit.core.presentation.utils

object ApiConstants {

    // Authentication constants
    const val KEY = "q3t6w9z\$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh"
    const val EXPIRATION_TIME = 1000 * 60 * 60 * 24

    const val HEADER_TOKEN = "x-access-token"
    const val PARAM_USER_ID = "userid"
    const val PARAM_USER_ID_UPPER_CASE = "userId"
    const val PARAM_LANGUAGE = "language"

    // PATHS
    private const val BASE_PATH = "/api/v2"
    const val AUTH_PATH = "$BASE_PATH/auth"
    const val DEBUG_PATH = "$BASE_PATH/debug"
    const val HISTORY_PATH = "$BASE_PATH/history"
    const val SHIPMENT_PATH = "$BASE_PATH/shipments"
    const val TRACKING_PATH = "$BASE_PATH/tracking"

    // ENDPOINTS
    const val ATTACH_TRACKER_URL = "/attachTracker"
    const val BROADCAST_LOCATION_URL = "/broadcastLocation"
    const val CREATE_TRIP_URL = "/createTrip"
    const val GENERATE_SHIPMENT_ID = "/generateId"
    const val GET_ACTIVE_TRIP_BY_DRIVER_ID_URL = "/getActiveTripByDriverId"
    const val GET_ATTACHED_SHIPMENT_URL = "/getAttachedShipment"
    const val GET_ATTACHED_TRIP_URL = "/getAttachedTrip"
    const val GET_LAST_LOCATION_URL = "/getLastLocation"
    const val GET_LOG_MESSAGES_URL = "/getLogMessages"
    const val UPDATE_SHIPMENT_STATUS_URL = "/updateShipmentStatus"
    const val UPDATE_TRIP_STATUS_URL = "/updateTripStatus"
    const val SAVE_DEBUG_LOG_URL = "/logMessage"
    const val SEND_EMAIL = "/sendEmail"
    const val SIGN_IN_URL = "/signIn"
    const val SIGN_UP_URL = "/signUp"

}